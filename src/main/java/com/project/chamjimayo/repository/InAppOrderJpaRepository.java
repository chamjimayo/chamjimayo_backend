package com.project.chamjimayo.repository;


import com.project.chamjimayo.repository.domain.entity.InAppOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InAppOrderJpaRepository extends JpaRepository<InAppOrder, Long> {
  InAppOrder findOrderByPurchaseToken(String purchaseToken);
}
