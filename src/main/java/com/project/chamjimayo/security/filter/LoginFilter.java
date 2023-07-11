package com.project.chamjimayo.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.chamjimayo.exception.AuthException;
import com.project.chamjimayo.security.AuthIdToken;
import com.project.chamjimayo.security.dto.LoginRequest;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class LoginFilter extends AbstractAuthenticationProcessingFilter {

  private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher(
      "/api/login",
      "POST");
  private final ObjectMapper objectMapper = new ObjectMapper();

  public LoginFilter() {
    super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {
    if (!request.getMethod().equals("POST")) {
      throw new AuthenticationServiceException(
          "Authentication method not supported: " + request.getMethod());
    }

    LoginRequest loginRequest;
    try {
      loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
    } catch (IOException e) {
      throw new AuthException("인증 오류가 발생했습니다.");
    }

    String authId = loginRequest.getAuthId();

    if (authId == null) {
      authId = "";
    }

    AuthIdToken authRequest = AuthIdToken.unauthenticated(authId);

    setDetails(request, authRequest);

    return this.getAuthenticationManager().authenticate(authRequest);
  }

  protected void setDetails(HttpServletRequest request, AuthIdToken authRequest) {
    authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
  }
}
