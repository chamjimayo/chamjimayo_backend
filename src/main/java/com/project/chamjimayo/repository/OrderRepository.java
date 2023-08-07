package com.project.chamjimayo.repository;


import com.project.chamjimayo.domain.entity.Order;
import com.project.chamjimayo.domain.entity.Restroom;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
  Order findOrderByPurchaseToken(String purchaseToken);
}
