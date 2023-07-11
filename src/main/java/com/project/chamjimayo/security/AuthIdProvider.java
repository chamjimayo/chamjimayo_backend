package com.project.chamjimayo.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@RequiredArgsConstructor
public class AuthIdProvider implements AuthenticationProvider {

  private final CustomUserDetailsService customUserDetailsService;

  @Override
  public Authentication authenticate(Authentication authentication)
      throws AuthenticationException {
    String authId = (String) authentication.getPrincipal();

    CustomUserDetails customUserDetails = (CustomUserDetails) customUserDetailsService
        .loadUserByAuthId(authId);

    return AuthIdToken.authenticated(authId, customUserDetails.getId(),
        customUserDetails.getAuthorities());
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return AuthIdToken.class.isAssignableFrom(authentication);
  }
}
