package com.project.chamjimayo.repository;

import com.project.chamjimayo.repository.domain.entity.Token;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {
  boolean existsTokenByRefreshToken(String refreshToken);
  Optional<Token> findTokenByUserId(String userId);
}
