package com.project.chamjimayo.controller.dto.request;

import com.project.chamjimayo.service.dto.GoogleInAppPurchaseDto;
import lombok.Getter;

@Getter
public class GoogleInAppPurchaseRequest {
  private final String productId;
  private final String token;

  public GoogleInAppPurchaseRequest(String productId, String token) {
    this.productId = productId;
    this.token = token;
  }

  public GoogleInAppPurchaseDto toDto() {
    return new GoogleInAppPurchaseDto(productId, token);
  }
}
