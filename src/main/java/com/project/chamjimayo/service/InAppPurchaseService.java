package com.project.chamjimayo.service;

import com.project.chamjimayo.controller.dto.request.GoogleInAppPurchaseRequest;
import com.project.chamjimayo.controller.dto.request.PointRequestDto;
import com.project.chamjimayo.controller.dto.response.PointResponseDto;
import com.project.chamjimayo.repository.domain.entity.Product;
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
  public PointResponseDto verifyPurchase(Long userId, GoogleInAppPurchaseRequest request) {
    if (receiptValidationService.validateReceipt(request)) {
      Integer point = Product.pointsFromProductId(request.getProductId());
      PointResponseDto pointChangeDto = userService.chargePoints(
          PointRequestDto.create(userId, point));

      orderService.createOrder(request.getToken(), pointChangeDto.getUserId(),
          pointChangeDto.getPoint());

      return pointChangeDto;
    }

    return PointResponseDto.create(userId, 0);
  }
}
