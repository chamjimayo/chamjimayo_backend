package com.project.chamjimayo.service;

import com.google.api.services.androidpublisher.AndroidPublisher;
import com.google.api.services.androidpublisher.AndroidPublisher.Purchases.Products;
import com.google.api.services.androidpublisher.model.ProductPurchase;
import com.project.chamjimayo.controller.config.GoogleProperties;
import com.project.chamjimayo.service.dto.GoogleInAppPurchaseDto;
import com.project.chamjimayo.service.exception.GoogleClientRequestException;
import com.project.chamjimayo.service.exception.PurchaseVerificationException;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReceiptValidationService {
  private static final Integer PURCHASE_CANCEL = 1;
  private static final Integer PURCHASE_PENDING = 2;

  private final AndroidPublisher androidPublisher;
  private final GoogleProperties googleProperties;
  
  public boolean validateReceipt(GoogleInAppPurchaseDto dto) {
    Products products = androidPublisher.purchases().products();

    ProductPurchase purchase = getProductPurchase(dto, products);

    if (purchase.getPurchaseState().equals(PURCHASE_CANCEL) || 
        purchase.getPurchaseState().equals(PURCHASE_PENDING)) {
      throw new PurchaseVerificationException("결제가 완료되지 않았습니다.");
    }

    return true;
  }

  private ProductPurchase getProductPurchase(GoogleInAppPurchaseDto dto,
      Products products) {
    ProductPurchase purchase;
    try {
      purchase = products
          .get(googleProperties.getGoogleApplicationPackageName(), dto.getProductId(),
              dto.getToken())
          .execute();
    } catch (IOException e) {
      throw new GoogleClientRequestException(e);
    }
    return purchase;
  }
}
