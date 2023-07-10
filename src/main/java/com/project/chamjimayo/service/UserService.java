package com.project.chamjimayo.service;

import com.project.chamjimayo.domain.entity.AuthType;
import com.project.chamjimayo.domain.entity.Role;
import com.project.chamjimayo.domain.entity.User;
import com.project.chamjimayo.exception.UserDuplicateException;
import com.project.chamjimayo.exception.UserNickNameDuplicateException;
import com.project.chamjimayo.repository.UserRepository;
import com.project.chamjimayo.security.JwtTokenProvider;
import com.project.chamjimayo.service.dto.AuthTokenDto;
import com.project.chamjimayo.service.dto.SignUpDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

  private final UserRepository userRepository;
  private final JwtTokenProvider jwtTokenProvider;

  public AuthTokenDto saveUser(SignUpDto dto) {
    validateDuplicateUser(dto.getAuthId(), dto.getNickname());

    User user = dto.createNewUser();

    Integer userId = userRepository.save(user).getUserId();

    String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(userId));

    return AuthTokenDto.create(accessToken);
  }

  private void validateDuplicateUser(String authId, String nickname) {
    if (userRepository.existsUserByNickname(nickname))
      throw new UserNickNameDuplicateException("사용자 닉네임이 중복되었습니다. 닉네임: " + nickname);
    else if (userRepository.existsUserByAuthId(authId))
      throw new UserDuplicateException("사용자가 존재합니다. 로그인을 시도하세요.");
  }
}
