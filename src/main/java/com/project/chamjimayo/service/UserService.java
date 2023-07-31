package com.project.chamjimayo.service;

import com.project.chamjimayo.controller.dto.PointChangeDto;
import com.project.chamjimayo.domain.entity.User;
import com.project.chamjimayo.exception.PointLackException;
import com.project.chamjimayo.exception.UserDuplicateException;
import com.project.chamjimayo.exception.UserNickNameDuplicateException;
import com.project.chamjimayo.exception.UserNotFoundException;
import com.project.chamjimayo.repository.RestroomQueryRepository;
import com.project.chamjimayo.repository.UserJpaRepository;
import com.project.chamjimayo.repository.UserQueryRepository;
import com.project.chamjimayo.service.dto.DuplicateCheckDto;
import com.project.chamjimayo.service.dto.RestroomSummaryDto;
import com.project.chamjimayo.service.dto.SignUpDto;
import com.project.chamjimayo.service.dto.UserDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

	private final UserJpaRepository userJpaRepository;
	private final UserQueryRepository userQueryRepository;
	private final RestroomQueryRepository restroomQueryRepository;

	public String saveUser(SignUpDto dto) {
		validateDuplicateUser(dto.getAuthId(), dto.getNickname());

		User user = dto.createNewUser();

		return String.valueOf(userJpaRepository.save(user).getUserId());
	}

	private void validateDuplicateUser(String authId, String nickname) {
		if (userJpaRepository.existsUserByNickname(nickname)) {
			throw new UserNickNameDuplicateException("사용자 닉네임이 중복되었습니다. 닉네임: " + nickname);
		} else if (userJpaRepository.existsUserByAuthId(authId)) {
			throw new UserDuplicateException("사용자가 존재합니다. 로그인을 시도하세요.");
		}
	}

	@Transactional(readOnly = true)
	public String getUserByAuthId(String authId) {
		User user = userJpaRepository.findUserByAuthId(authId)
			.orElseThrow(() -> new UserNotFoundException("사용자를 찾지 못했습니다."));

		return String.valueOf(user.getUserId());
	}

	@Transactional(readOnly = true)
	public DuplicateCheckDto isNicknameDuplicate(String nickname) {
		return DuplicateCheckDto.create(userJpaRepository.existsUserByNickname(nickname));
	}

	@Transactional(readOnly = true)
	public UserDetailsDto getUserDetails(Long id) {
		return userQueryRepository.findUserDetailsById(id)
			.orElseThrow(() -> new UserNotFoundException("사용자를 찾지 못했습니다."));
	}

	@Transactional(readOnly = true)
	public RestroomSummaryDto getUsingRestroom(Long id) {
		return restroomQueryRepository.findUsingRestRoomDtoByUserId(id);
	}

	@Transactional(readOnly = true)
	public Page<RestroomSummaryDto> getUsedRestrooms(Long id, Pageable pageable) {
		return restroomQueryRepository.findUsedRestroomDtosByUserIdAndPageable(id, pageable);
	}

	/**
	 * 해당 유저의 포인트를 충전합니다. (반환값 : 유저Id, 충전 후 포인트)
	 */
	@Transactional
	public PointChangeDto chargePoints(PointChangeDto requestDTO) {
		Long userId = requestDTO.getUserId();
		User user = userJpaRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException("유저를 찾지 못했습니다. ID: " + userId));

		Integer currentPoint = user.getPoint();
		if (currentPoint == null) {
			currentPoint = 0;
		}
		Integer newPoint = requestDTO.getPoint();

		user.addPoint(currentPoint, newPoint);

		PointChangeDto responseDTO = PointChangeDto.create(userId, user.getPoint());

		return responseDTO;
	}

	/**
	 * 해당 유저의 포인트를 차감합니다. (반환값 : 유저Id, 차감 후 포인트)
	 */
	@Transactional
	public PointChangeDto deductPoints(PointChangeDto requestDTO) {
		Long userId = requestDTO.getUserId();
		User user = userJpaRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException("유저를 찾지 못했습니다. ID: " + userId));

		Integer currentPoint = user.getPoint();
		if (currentPoint == null) {
			currentPoint = 0;
		}
		Integer deductionPoint = requestDTO.getPoint();

		if (deductionPoint > currentPoint) {
			throw new PointLackException("포인트가 부족합니다.");
		}

		user.deductPoint(currentPoint, deductionPoint);

		PointChangeDto responseDTO = PointChangeDto.create(userId, user.getPoint());

		return responseDTO;
	}
}
