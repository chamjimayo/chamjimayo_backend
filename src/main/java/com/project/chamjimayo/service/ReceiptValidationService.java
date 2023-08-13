package com.project.chamjimayo.service;

import com.google.api.services.androidpublisher.AndroidPublisher;
import com.google.api.services.androidpublisher.AndroidPublisher.Purchases.Products;
import com.google.api.services.androidpublisher.model.ProductPurchase;
import com.project.chamjimayo.controller.config.GoogleProperties;
import com.project.chamjimayo.controller.dto.request.GoogleInAppPurchaseRequest;
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
  
  public boolean validateReceipt(GoogleInAppPurchaseRequest request) {
    Products products = androidPublisher.purchases().products();

    ProductPurchase purchase = getProductPurchase(request, products);

    if (purchase.getPurchaseState().equals(PURCHASE_CANCEL) || 
        purchase.getPurchaseState().equals(PURCHASE_PENDING)) {
      throw new PurchaseVerificationException("결제가 완료되지 않았습니다.");
    }

    return true;
  }

  private ProductPurchase getProductPurchase(GoogleInAppPurchaseRequest request,
      Products products) {
    ProductPurchase purchase;
    try {
      purchase = products
          .get(googleProperties.getGoogleApplicationPackageName(),
              request.getProductId(), request.getToken())
          .execute();
    } catch (IOException e) {
      throw new GoogleClientRequestException(e);
    }
    return purchase;
  }
}
