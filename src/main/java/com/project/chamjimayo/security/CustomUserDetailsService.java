package com.project.chamjimayo.security;

import com.project.chamjimayo.domain.entity.User;
import com.project.chamjimayo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Transactional(readOnly = true)
  @Override
  public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
    User user = userRepository.findById(Long.valueOf(id))
        .orElseThrow(() ->
            new UsernameNotFoundException("유저를 찾을 수 없습니다. id: " + id)
        );

    return CustomUserDetails.create(user.getUserId(), user.getRole());
  }
}
