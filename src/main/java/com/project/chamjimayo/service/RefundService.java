package com.project.chamjimayo.service;

import com.google.api.services.androidpublisher.AndroidPublisher;
import com.google.api.services.androidpublisher.model.VoidedPurchase;
import com.google.api.services.androidpublisher.model.VoidedPurchasesListResponse;
import com.project.chamjimayo.controller.config.GoogleProperties;
import com.project.chamjimayo.controller.dto.response.RefundResponse;
import com.project.chamjimayo.repository.InAppOrderJpaRepository;
import com.project.chamjimayo.repository.UserJpaRepository;
import com.project.chamjimayo.repository.domain.entity.InAppOrder;
import com.project.chamjimayo.repository.domain.entity.User;
import com.project.chamjimayo.service.exception.IoException;
import com.project.chamjimayo.service.exception.UserNotFoundException;
import com.project.chamjimayo.service.exception.VoidedPurchaseNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefundService {

  private final InAppOrderJpaRepository inAppOrderJpaRepository;
  private final UserJpaRepository userJpaRepository;
  private final AndroidPublisher androidPublisher;
  private final GoogleProperties googleProperties;

  @Transactional
  public List<RefundResponse> processRefund() {
    List<VoidedPurchase> voidedPurchases = getVoidedPurchases();

    List<RefundResponse> refundResponseList = new ArrayList<>();
    for (VoidedPurchase voidedPurchase : voidedPurchases) {
      InAppOrder inAppOrder = inAppOrderJpaRepository.findOrderByPurchaseToken(voidedPurchase.getPurchaseToken());
      if (!inAppOrder.isAlreadyRefund()) { //환불 처리된게 아니라면 환불 처리
        refund(refundResponseList, inAppOrder);
      }
    }
    return refundResponseList;
  }

  private void refund(List<RefundResponse> refundResponseList, InAppOrder inAppOrder) {
    User user = userJpaRepository.findUserByUserId(inAppOrder.getUserId())
        .orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다"));
    //포인트 환불, 이미 사용했다면 보유 포인트 마이너스
    user.deductPoint(inAppOrder.getPoint());
    // 환불 처리 목록에 추가
    refundResponseList.add(new RefundResponse(user.getUserId(), inAppOrder.getPoint()));
    inAppOrder.alreadyRefund(); // order 테이블에서 이미 처리된 환불 요청으로 변경
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
