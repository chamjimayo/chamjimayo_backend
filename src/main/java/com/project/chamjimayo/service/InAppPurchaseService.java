package com.project.chamjimayo.service;

import com.project.chamjimayo.controller.dto.PointChangeDto;
import com.project.chamjimayo.repository.domain.entity.Product;
import com.project.chamjimayo.service.dto.GoogleInAppPurchaseDto;
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
  public PointChangeDto verifyPurchase(Long userId, GoogleInAppPurchaseDto dto) {
    if (receiptValidationService.validateReceipt(dto)) {
      Integer point = Product.pointsFromProductId(dto.getProductId());
      PointChangeDto pointChangeDto = userService.chargePoints(
          PointChangeDto.create(userId, point));

      orderService.createOrder(dto.getToken(), pointChangeDto.getUserId(),
          pointChangeDto.getPoint());

      return pointChangeDto;
    }

    return PointChangeDto.create(userId, 0);
  }
}
