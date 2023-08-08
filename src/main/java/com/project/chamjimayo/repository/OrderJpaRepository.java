package com.project.chamjimayo.repository;

import com.project.chamjimayo.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<Order, String> {

}
