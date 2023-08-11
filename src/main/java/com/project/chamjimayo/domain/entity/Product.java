package com.project.chamjimayo.domain.entity;

public enum Product {
  POINT_1000("point_1000", 1000),
  POINT_3000("point_3000", 3000),
  POINT_5000("point_5000", 5000),
  POINT_8000("point_8000", 8000),
  POINT_10000("point_10000", 10000);

  private final String productId;
  private final int points;

  Product(String productId, int points) {
    this.productId = productId;
    this.points = points;
  }

  public static Integer pointsFromProductId(String productId) {
    for (Product product : values()) {
      if (product.productId.equals(productId)) {
        return product.points;
      }
    }

    throw new IllegalArgumentException("No product found for id: " + productId);
  }
}
