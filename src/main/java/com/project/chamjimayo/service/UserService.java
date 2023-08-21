package com.project.chamjimayo.service;

import com.project.chamjimayo.controller.exception.JsonFileNotFoundException;
import com.project.chamjimayo.repository.RestroomQueryRepository;
import com.project.chamjimayo.repository.UserJpaRepository;
import com.project.chamjimayo.repository.UserQueryRepository;
import com.project.chamjimayo.repository.domain.entity.User;
import com.project.chamjimayo.service.dto.DuplicateCheckDto;
import com.project.chamjimayo.service.dto.PointDto;
import com.project.chamjimayo.service.dto.RestroomSummaryDto;
import com.project.chamjimayo.service.dto.SignUpDto;
import com.project.chamjimayo.service.dto.UserAttributeChangeDto;
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

    User user = dto.toEntity();

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
   * 해당 유저의 포인트를 충전합니다. (반환값 : 충전 후 포인트)
   */
  public PointDto chargePoints(Long userId, PointDto pointDto) {

    if (userId == null) {
      throw new JsonFileNotFoundException("userId를 입력해주세요.");
    }

    User user = getUser(userId);

    Integer newPoint = pointDto.getPoint();

    user.addPoint(newPoint);

    return PointDto.create(user.getPoint());
  }

  /**
   * 해당 유저의 포인트를 차감합니다. (반환값 : 차감 후 포인트)
   */
  public PointDto deductPoints(Long userId, PointDto pointDto) {

    if (userId == null) {
      throw new JsonFileNotFoundException("userId를 입력해주세요.");
    }

    User user = getUser(userId);

    Integer deductionPoint = pointDto.getPoint();

    if (deductionPoint > user.getPoint()) {
      throw new PointLackException("포인트가 부족합니다.");
    }

    user.deductPoint(deductionPoint);

    return PointDto.create(user.getPoint());
  }

  private User getUser(Long userId) {
    return userJpaRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException("유저를 찾지 못했습니다. ID: "
            + userId));
  }

  public UserAttributeChangeDto changeNickName(Long id, UserAttributeChangeDto dto) {
    User user = getUser(id);

    user.changeNickname(dto.getAttribute());

    return UserAttributeChangeDto.create(user.getNickname());
  }

  public UserAttributeChangeDto changeUserProfile(Long id, UserAttributeChangeDto dto) {
    DuplicateCheckDto duplicateCheckDto = isNicknameDuplicate(dto.getAttribute());
    if (duplicateCheckDto.isDuplicate()) {
      throw new UserNickNameDuplicateException("사용자 닉네임이 중복되었습니다.");
    }

    User user = getUser(id);

    user.changeUserProfile(dto.getAttribute());

    return UserAttributeChangeDto.create(user.getUserProfile());
  }
}
