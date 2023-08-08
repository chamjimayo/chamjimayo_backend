package com.project.chamjimayo.domain.entity;

public enum Product {
  PRODUCT_A("product_a", 100),
  PRODUCT_B("product_b", 200),
  PRODUCT_C("product_c", 300);

  private final String productId;
  private final int points;

  Product(String productId, int points) {
    this.productId = productId;
    this.points = points;
  }

  public String getProductId() {
    return productId;
  }

  public int getPoints() {
    return points;
  }

  // This method can be used to get the corresponding Product enum for a product id
  public static Integer pointsFromProductId(String productId) {
    for (Product product : values()) {
      if (product.getProductId().equals(productId)) {
        return product.getPoints();
      }
    }

    throw new IllegalArgumentException("No product found for id: " + productId);
  }
}
