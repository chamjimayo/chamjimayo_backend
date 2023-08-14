package com.project.chamjimayo.service;

import com.project.chamjimayo.controller.dto.request.GoogleInAppPurchaseRequest;
import com.project.chamjimayo.controller.dto.request.PointRequest;
import com.project.chamjimayo.controller.dto.response.PointResponse;
import com.project.chamjimayo.repository.domain.entity.Product;
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
  public PointResponse verifyPurchase(Long userId, GoogleInAppPurchaseRequest request) {
    if (receiptValidationService.validateReceipt(request)) {
      Integer point = Product.pointsFromProductId(request.getProductId());
      PointResponse pointResponse = userService.chargePoints(
          PointDto.create(userId, point));

      orderService.createOrder(request.getToken(), pointResponse.getUserId(),
          pointResponse.getPoint());

      return pointResponse;
    }

    return PointResponse.create(userId, 0);
  }
}
