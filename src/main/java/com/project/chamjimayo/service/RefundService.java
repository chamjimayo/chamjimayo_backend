package com.project.chamjimayo.service;

import com.google.api.services.androidpublisher.AndroidPublisher;
import com.google.api.services.androidpublisher.model.VoidedPurchase;
import com.google.api.services.androidpublisher.model.VoidedPurchasesListResponse;
import com.project.chamjimayo.controller.config.GoogleProperties;
import com.project.chamjimayo.controller.dto.RefundResult;
import com.project.chamjimayo.domain.entity.Order;
import com.project.chamjimayo.domain.entity.User;
import com.project.chamjimayo.exception.IoException;
import com.project.chamjimayo.exception.UserNotFoundException;
import com.project.chamjimayo.exception.VoidedPurchaseNotFoundException;
import com.project.chamjimayo.repository.OrderRepository;
import com.project.chamjimayo.repository.UserJpaRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefundService {

  private final OrderRepository orderRepository;
  private final UserJpaRepository userJpaRepository;
  private final AndroidPublisher androidPublisher;
  private final GoogleProperties googleProperties;

  @Transactional
  public List<RefundResult> processRefund() {
    List<VoidedPurchase> voidedPurchases = getVoidedPurchases();

    List<RefundResult> refundResultList = new ArrayList<>();
    for (VoidedPurchase voidedPurchase : voidedPurchases) {
      Order order = orderRepository.findOrderByPurchaseToken(voidedPurchase.getPurchaseToken());
      if (!order.isAlreadyRefund()) { //환불 처리된게 아니라면 환불 처리
        refund(refundResultList, order);
      }
    }
    return refundResultList;
  }

  private void refund(List<RefundResult> refundResultList, Order order) {
    User user = userJpaRepository.findUserByUserId(order.getUserId())
            .orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다"));
    //포인트 환불, 이미 사용했다면 보유 포인트 마이너스
    user.deductPoint(user.getPoint(), order.getPoint());
    // 환불 처리 목록에 추가
    refundResultList.add(new RefundResult(user.getUserId(), order.getPoint()));
    order.alreadyRefund(); // order 테이블에서 이미 처리된 환불 요청으로 변경
  }

  private List<VoidedPurchase> getVoidedPurchases() {
    VoidedPurchasesListResponse response;
    try {
      response = androidPublisher
          .purchases().voidedpurchases()
          .list(googleProperties.getGoogleApplicationPackageName())
          .execute();
    } catch (IOException e) {
      throw new IoException("패키지명 입출력 오류");
    }

    List<VoidedPurchase> result = response.getVoidedPurchases();
    if (result == null) {
      throw new VoidedPurchaseNotFoundException("무효화된 거래 내역이 없습니다");
    }

    return result;
  }
}
