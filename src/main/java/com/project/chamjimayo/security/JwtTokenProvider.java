package com.project.chamjimayo.security;

import com.project.chamjimayo.exception.AuthException;
import com.project.chamjimayo.exception.InvalidTokenException;
import com.project.chamjimayo.repository.TokenRepository;
import com.project.chamjimayo.security.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

  private final SecretKey key;
  private final long accessTokenValidityInMilliseconds;
  private final long refreshTokenValidityInMilliseconds;
  private final TokenRepository tokenRepository;

  public JwtTokenProvider(JwtProperties jwtProperties, TokenRepository tokenRepository) {
    this.key = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));
    this.accessTokenValidityInMilliseconds = jwtProperties.getAccessTokenValidityInMilliseconds();
    this.refreshTokenValidityInMilliseconds = jwtProperties.getRefreshTokenValidityInMilliseconds();
    this.tokenRepository = tokenRepository;
  }

  public String createAccessToken(final String payload) {
    return createToken(payload, accessTokenValidityInMilliseconds);
  }

  public String createRefreshToken(final String payload) {
    return createToken(payload, refreshTokenValidityInMilliseconds);
  }

  private String createToken(final String payload, final Long validityInMilliseconds) {
    Date now = new Date();
    Date validity = new Date(now.getTime() + validityInMilliseconds);

    return Jwts.builder()
        .setSubject(payload)
        .setIssuedAt(now)
        .setExpiration(validity)
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
  }

  public String getPayload(final String token) {
    try {
      return Jwts.parserBuilder()
          .setSigningKey(key)
          .build()
          .parseClaimsJws(token)
          .getBody()
          .getSubject();
    } catch (final JwtException | IllegalArgumentException e) {
      throw new InvalidTokenException("유효하지 않은 토큰입니다.");
    }
  }

  public boolean validateToken(final String token) {
    try {
      Jws<Claims> claims = Jwts.parserBuilder()
          .setSigningKey(key)
          .build()
          .parseClaimsJws(token);

      return claims.getBody()
          .getExpiration()
          .before(new Date());
    } catch (final JwtException | IllegalArgumentException e) {
      throw new InvalidTokenException("유효하지 않은 토큰입니다.");
    }
  }

  public String getRefreshToken(String userId) {
    return tokenRepository.findById(Long.valueOf(userId))
        .orElseThrow(() -> new AuthException("사용자의 refresh token을 찾을 수 없습니다."))
        .getRefreshToken();
  }
}
