package com.project.chamjimayo.service;

import com.project.chamjimayo.controller.dto.PointChargeDto;
import com.project.chamjimayo.domain.entity.User;
import com.project.chamjimayo.exception.UserDuplicateException;
import com.project.chamjimayo.exception.UserNickNameDuplicateException;
import com.project.chamjimayo.exception.UserNotFoundException;
import com.project.chamjimayo.repository.UserJpaRepository;
import com.project.chamjimayo.service.dto.DuplicateCheckDto;
import com.project.chamjimayo.service.dto.SignUpDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

  private final UserJpaRepository userJpaRepository;

  public String saveUser(SignUpDto dto) {
    validateDuplicateUser(dto.getAuthId(), dto.getNickname());

    User user = dto.createNewUser();

    return String.valueOf(userJpaRepository.save(user).getUserId());
  }

  private void validateDuplicateUser(String authId, String nickname) {
    if (userJpaRepository.existsUserByNickname(nickname))
      throw new UserNickNameDuplicateException("사용자 닉네임이 중복되었습니다. 닉네임: " + nickname);
    else if (userJpaRepository.existsUserByAuthId(authId))
      throw new UserDuplicateException("사용자가 존재합니다. 로그인을 시도하세요.");
  }

  @Transactional(readOnly = true)
  public String getUserByAuthId(String authId) {
    User user = userJpaRepository.findUserByAuthId(authId)
        .orElseThrow(() -> new UserNotFoundException("사용자를 찾지 못했습니다."));

    return String.valueOf(user.getUserId());
  }

  public DuplicateCheckDto isNicknameDuplicate(String nickname) {
    return DuplicateCheckDto.create(userJpaRepository.existsUserByNickname(nickname));
  }

  /**
   * 해당 유저의 포인트를 충전합니다. (반환값 : 유저Id, 합산 포인트)
   */
  @Transactional
  public PointChargeDto chargePoints(PointChargeDto requestDTO) {
    Long userId = requestDTO.getUserId();
    Integer Point = requestDTO.getPoint();
    User user = userJpaRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException("유저를 찾지 못했습니다. ID: " + userId));

    int currentPoint = user.getPoint() != null ? user.getPoint() : 0;
    int newPoint = requestDTO.getPoint();
    user.addPoint(currentPoint, newPoint);

    userJpaRepository.save(user);

    PointChargeDto responseDTO = PointChargeDto.create(userId, Point);

    return responseDTO;
  }
}
