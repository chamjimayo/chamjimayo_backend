package com.project.chamjimayo.service;

import com.project.chamjimayo.controller.dto.request.PointRequestDto;
import com.project.chamjimayo.controller.dto.response.PointResponseDto;
import com.project.chamjimayo.repository.RestroomQueryRepository;
import com.project.chamjimayo.repository.UserJpaRepository;
import com.project.chamjimayo.repository.UserQueryRepository;
import com.project.chamjimayo.repository.domain.entity.User;
import com.project.chamjimayo.service.dto.DuplicateCheckDto;
import com.project.chamjimayo.service.dto.RestroomSummaryDto;
import com.project.chamjimayo.service.dto.SignUpDto;
import com.project.chamjimayo.service.dto.UserDetailsDto;
import com.project.chamjimayo.service.exception.PointLackException;
import com.project.chamjimayo.service.exception.UserDuplicateException;
import com.project.chamjimayo.service.exception.UserNickNameDuplicateException;
import com.project.chamjimayo.service.exception.UserNotFoundException;
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
  public PointResponseDto chargePoints(PointRequestDto requestDTO) {
    User user = getUser(requestDTO);

    Integer newPoint = requestDTO.getPoint();

    user.addPoint(newPoint);

    return PointResponseDto.create(user.getUserId(), user.getPoint());
  }

  /**
   * 해당 유저의 포인트를 차감합니다. (반환값 : 유저Id, 차감 후 포인트)
   */
  @Transactional
  public PointResponseDto deductPoints(PointRequestDto requestDTO) {
    User user = getUser(requestDTO);

    Integer deductionPoint = requestDTO.getPoint();

    if (deductionPoint > user.getPoint()) {
      throw new PointLackException("포인트가 부족합니다.");
    }

    user.deductPoint(deductionPoint);

    return PointResponseDto.create(user.getUserId(), user.getPoint());
  }

  private User getUser(PointRequestDto requestDto) {
    return userJpaRepository.findById(requestDto.getUserId())
        .orElseThrow(() -> new UserNotFoundException("유저를 찾지 못했습니다. ID: "
            + requestDto.getUserId()));
  }
}
