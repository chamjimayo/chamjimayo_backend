package com.project.chamjimayo.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Token {
  @Id
  @GeneratedValue
  private Long id;

  private String userId;

  private String refreshToken;
}
