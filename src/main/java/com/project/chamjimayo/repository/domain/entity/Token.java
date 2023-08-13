package com.project.chamjimayo.repository.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Token {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String userId;

  private String refreshToken;

  public Token(String userId, String refreshToken) {
    this.userId = userId;
    this.refreshToken = refreshToken;
  }

  public static Token create(String userId, String refreshToken) {
    return new Token(userId, refreshToken);
  }

  public void changeRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }
}
