package com.project.chamjimayo.service;

import com.project.chamjimayo.repository.domain.entity.Product;
import com.project.chamjimayo.service.dto.GoogleInAppPurchaseDto;
import com.project.chamjimayo.service.dto.PointDto;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class InAppPurchaseService {

  private final UserService userService;
  private final OrderService orderService;
  private final ReceiptValidationService receiptValidationService;

  @Transactional
  public PointDto verifyPurchase(Long userId, GoogleInAppPurchaseDto dto) {
    if (receiptValidationService.validateReceipt(dto)) {
      Integer point = Product.pointsFromProductId(dto.getProductId());
      PointDto pointDto = userService.chargePoints(userId, PointDto.create(point));

      orderService.createOrder(dto.getToken(), userId, pointDto.getPoint());

      return pointDto;
    }

    return PointDto.create(0);
  }
}
