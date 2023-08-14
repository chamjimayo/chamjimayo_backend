package com.project.chamjimayo.service.dto;

import lombok.Getter;

@Getter
public class GoogleInAppPurchaseDto {
  private final String productId;
  private final String token;

  public GoogleInAppPurchaseDto(String productId, String token) {
    this.productId = productId;
    this.token = token;
  }
}
