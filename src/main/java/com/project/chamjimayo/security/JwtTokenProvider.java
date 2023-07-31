package com.project.chamjimayo.security;

import com.project.chamjimayo.exception.InvalidTokenException;
import com.project.chamjimayo.security.config.JwtProperties;
import io.jsonwebtoken.ExpiredJwtException;
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
	private final long accessTokenValidityMs;
	private final long refreshTokenValidityMs;

	public JwtTokenProvider(JwtProperties jwtProperties) {
		this.key = Keys.hmacShaKeyFor(
			jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));
		this.accessTokenValidityMs = jwtProperties.getAccessTokenValidityMs();
		this.refreshTokenValidityMs = jwtProperties.getRefreshTokenValidityMs();
	}

	public String createAccessToken(final String payload) {
		return createToken(payload, accessTokenValidityMs);
	}

	public String createRefreshToken(final String payload) {
		return createToken(payload, refreshTokenValidityMs);
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

	public boolean isValid(final String token) {
		try {
			parseJws(token);

			return true;
		} catch (final JwtException | IllegalArgumentException e) {
			return false;
		}
	}

	public boolean isExpired(final String token) {
		try {
			parseJws(token);

			return false;
		} catch (ExpiredJwtException e) {
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			throw new InvalidTokenException("유효하지 않은 토큰입니다.");
		}
	}

	private void parseJws(String token) {
		Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token);
	}
}
