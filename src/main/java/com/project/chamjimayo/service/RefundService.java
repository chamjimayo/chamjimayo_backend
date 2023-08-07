package com.project.chamjimayo.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.androidpublisher.AndroidPublisher;
import com.google.api.services.androidpublisher.AndroidPublisherScopes;
import com.google.api.services.androidpublisher.model.VoidedPurchase;
import com.google.api.services.androidpublisher.model.VoidedPurchasesListResponse;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.project.chamjimayo.controller.dto.RefundResult;
import com.project.chamjimayo.domain.entity.Order;
import com.project.chamjimayo.domain.entity.User;
import com.project.chamjimayo.exception.IoException;
import com.project.chamjimayo.exception.UserNotFoundException;
import com.project.chamjimayo.exception.VoidedPurchaseNotFoundException;
import com.project.chamjimayo.repository.OrderRepository;
import com.project.chamjimayo.repository.UserJpaRepository;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefundService {

  private final OrderRepository orderRepository;
  private final UserJpaRepository userJpaRepository;
  private static final String APPLICATION_NAME = "com.umc.chamma (unreviewed)"; // // 추후 구글 개발자 계정 정보 받은 후 변경예정
  private static final String CREDENTIALS_PATH = "config/pc-api-9088886599585804524-472-4fe6020aba5c.json"; // 구글 서비스 계정의 인증 정보
  private static final String PACKAGE_NAME = "com.umc.chamma"; // 추후 구글 개발자 계정 정보 받은 후 변경예정

  @Transactional
  public List<RefundResult> processRefund() {
    Optional<List<VoidedPurchase>> voidedPurchases = Optional.ofNullable(Optional.ofNullable(
            getVoidedPurchases(PACKAGE_NAME))
        .orElseThrow(() -> new VoidedPurchaseNotFoundException("무효화된 거래 내역이 없습니다")));
    List<RefundResult> refundResultList = new ArrayList<>();
    for (VoidedPurchase voidedPurchase : voidedPurchases.get()) {
      Order order = orderRepository.findOrderByPurchaseToken(voidedPurchase.getPurchaseToken());
      if (!order.isAlreadyRefund()) { //환불 처리된게 아니라면 환불 처리
        Optional<User> user = Optional.ofNullable(
            userJpaRepository.findUserByUserId(order.getUserId())
                .orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다")));
        //포인트 환불, 이미 사용했다면 보유 포인트 마이너스
        user.get().deductPoint(user.get().getPoint(), order.getPoint());
        // 환불 처리 목록에 추가
        refundResultList.add(new RefundResult(user.get().getUserId(), order.getPoint()));
        order.alreadyRefund(); // order 테이블에서 이미 처리된 환불 요청으로 변경
      }
    }
    return refundResultList;
  }

  private List<VoidedPurchase> getVoidedPurchases(String packageName) {
    HttpTransport httpTransport = null;
    try {
      httpTransport = GoogleNetHttpTransport.newTrustedTransport();
    } catch (GeneralSecurityException e) {
      throw new com.project.chamjimayo.exception.GeneralSecurityException("Https 보안 예외 발생");
    } catch (IOException e) {
      throw new IoException("입출력 오류");
    }
    JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
    GoogleCredentials credentials = null;
    try {
      ClassLoader classLoader = RefundService.class.getClassLoader();
      // 리소스를 읽어오기 위해 ClassLoader의 getResourceAsStream 메서드를 사용합니다.
      InputStream inputStream = classLoader.getResourceAsStream(CREDENTIALS_PATH);
      credentials = GoogleCredentials.fromStream(
              inputStream)
          .createScoped(AndroidPublisherScopes.all());
    } catch (IOException e) {
      throw new IoException("구글계정 인증정보 파일 입출력 오류");
    }
    AndroidPublisher androidPublisher = new AndroidPublisher.Builder(httpTransport, jsonFactory,
        new HttpCredentialsAdapter(credentials))
        .setApplicationName(APPLICATION_NAME)
        .build();
    AndroidPublisher.Purchases.Voidedpurchases.List request =
        null;
    try {
      request = androidPublisher.purchases().voidedpurchases().list(packageName);
    } catch (IOException e) {
      throw new IoException("패키지명 입출력 오류");
    }
    VoidedPurchasesListResponse response = null;
    try {
      response = request.execute();
    } catch (IOException e) {
      throw new IoException("환불 리스트 입출력 오류");
    }
    return response.getVoidedPurchases();
  }
}
