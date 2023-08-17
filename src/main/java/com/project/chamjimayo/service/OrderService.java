package com.project.chamjimayo.service;

import com.project.chamjimayo.repository.InAppOrderJpaRepository;
import com.project.chamjimayo.repository.domain.entity.InAppOrder;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
  private final InAppOrderJpaRepository inAppOrderJpaRepository;

  public void createOrder(String purchaseToken, Long userId, Integer point) {
    inAppOrderJpaRepository.save(InAppOrder.create(purchaseToken, userId, point));
  }
}
