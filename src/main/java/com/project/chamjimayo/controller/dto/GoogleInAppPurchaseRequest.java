package com.project.chamjimayo.controller.dto;

import lombok.Getter;

@Getter
public class GoogleInAppPurchaseRequest {
  private final String productId;
  private final String token;

  public GoogleInAppPurchaseRequest(String productId, String token) {
    this.productId = productId;
    this.token = token;
  }
}
