package com.project.chamjimayo.security;

import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.util.Assert;

public class AuthIdToken extends AbstractAuthenticationToken {

  private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

  private final String authId;
  private final String userId;

  public AuthIdToken(String authId) {
    super(null);
    this.authId = authId;
    this.userId = null;
    setAuthenticated(false);
  }

  public AuthIdToken(String authId, String userId,
      Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
    this.authId = authId;
    this.userId = userId;
    super.setAuthenticated(true);
  }

  public static AuthIdToken unauthenticated(String authId) {
    return new AuthIdToken(authId);
  }

  public static AuthIdToken authenticated(String authId, String userId,
      Collection<? extends GrantedAuthority> authorities) {
    return new AuthIdToken(authId, userId, authorities);
  }

  @Override
  public Object getCredentials() {
    return null;
  }

  @Override
  public Object getPrincipal() {
    return this.authId;
  }

  @Override
  public String getName() {
    return this.userId;
  }

  @Override
  public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
    Assert.isTrue(!isAuthenticated,
        "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
    super.setAuthenticated(false);
  }
}
