package com.project.chamjimayo.security;

import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

@RequiredArgsConstructor
public class ApiKeyAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {

  private final String principalRequestHeader;

  @Override
  protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
    String key = request.getHeader(principalRequestHeader);
    if (key == null) {
      throw new BadCredentialsException("Api 키가 올바르지 않습니다.");
    }

    return key;
  }

  @Override
  protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
    return "N/A";
  }
}
