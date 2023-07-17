package com.project.chamjimayo.service;

import com.project.chamjimayo.domain.entity.User;
import com.project.chamjimayo.exception.UserDuplicateException;
import com.project.chamjimayo.exception.UserNickNameDuplicateException;
import com.project.chamjimayo.exception.UserNotFoundException;
import com.project.chamjimayo.repository.RestroomQueryRepository;
import com.project.chamjimayo.repository.RestroomJpaRepository;
import com.project.chamjimayo.repository.UserQueryRepository;
import com.project.chamjimayo.repository.UserJpaRepository;
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

  public UserDetailsDto getUserDetails(Long id) {
    return userQueryRepository.findUserDetailsById(id)
        .orElseThrow(() -> new UserNotFoundException("사용자를 찾지 못했습니다."));
  }

  public RestroomSummaryDto getUsingRestroom(Long id) {
    return restroomQueryRepository.findUsingRestRoomDtoByUserId(id);
  }

  public Page<RestroomSummaryDto> getUsedRestrooms(Long id, Pageable pageable) {
    return restroomQueryRepository.findUsedRestroomDtosByUserIdAndPageable(id, pageable);
  }
}
