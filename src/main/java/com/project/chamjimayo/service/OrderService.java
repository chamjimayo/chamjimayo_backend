package com.project.chamjimayo.service;

import com.project.chamjimayo.repository.domain.entity.Order;
import com.project.chamjimayo.repository.OrderJpaRepository;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
  private final OrderJpaRepository orderJpaRepository;

  public void createOrder(String purchaseToken, Long userId, Integer point) {
    orderJpaRepository.save(Order.create(purchaseToken, userId, point));
  }
}
