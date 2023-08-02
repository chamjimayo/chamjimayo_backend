package com.project.chamjimayo.service;

import com.project.chamjimayo.controller.dto.GoogleInAppPurchaseRequest;
import com.project.chamjimayo.controller.dto.PointChangeDto;
import com.project.chamjimayo.domain.entity.Product;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class InAppPurchaseService {
  private final UserService userService;
  private final ReceiptValidationService receiptValidationService;

  @Transactional
  public void processPurchase(Long userId, GoogleInAppPurchaseRequest request) {
    if (receiptValidationService.validateReceipt(request)) {
      userService.chargePoints(PointChangeDto.create(userId,
          Product.pointsFromProductId(request.getProductId())));
    }
  }
}
