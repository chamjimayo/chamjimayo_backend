package com.project.chamjimayo.repository.domain.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity{
  @Id
  private String purchaseToken;
  private Long userId;
  private Integer point;
  private boolean alreadyRefund;

  public Order(String purchaseToken, Long userId, Integer point) {
    this.purchaseToken = purchaseToken;
    this.userId = userId;
    this.point = point;
    this.alreadyRefund = false;
  }

  public static Order create(String purchaseToken, Long userId, Integer point) {
    if (!validateFields(purchaseToken, userId, point)) {
      throw new IllegalArgumentException("토큰과 아이디는 비어있거나 널이면 안되고 포인트는 0보다 커야합니다.");
    }
    return new Order(purchaseToken,userId, point);
  }

  private static boolean validateFields(String purchaseToken, Long userId, Integer point) {
    return (purchaseToken != null && !purchaseToken.isEmpty()) &&
        (userId != null) &&
        (point != null && point != 0);
  }

  public void alreadyRefund(){
    this.alreadyRefund = true;
  }
}