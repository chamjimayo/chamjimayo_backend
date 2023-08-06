package com.project.chamjimayo.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.chamjimayo.domain.entity.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ReviewResponseDto {

  @Schema(type = "Long", example = "1")
  private Long reviewId;

  @Schema(type = "Long", example = "1")
  private Long userId;

  @Schema(type = "Long", example = "1")
  private Long restroomId;

  @Schema(type = "string", example = "깔끔해요!")
  private String reviewContent;

  @Schema(type = "Integer", example = "4")
  private Integer rating;

  @Schema(type = "string", example = "2023-07-29")
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDateTime dateTime;

  private ReviewResponseDto(Long reviewId, Long userId, Long restroomId, String reviewContent,
      Integer rating, LocalDateTime dateTime) {
    this.reviewId = reviewId;
    this.userId = userId;
    this.restroomId = restroomId;
    this.reviewContent = reviewContent;
    this.rating = rating;
    this.dateTime = dateTime;
  }

  public static ReviewResponseDto create(Long reviewId, Long userId, Long restroomId,
      String reviewContent, Integer rating, LocalDateTime dateTime) {
    return new ReviewResponseDto(reviewId, userId, restroomId, reviewContent, rating, dateTime);
  }

  public static ReviewResponseDto fromEntity(Review review) {
    return new ReviewResponseDto(review.getReviewId(), review.getUser().getUserId(),
        review.getRestroom().getRestroomId(), review.getReviewContent(), review.getRating(),
        review.getUpdatedDate());
  }
}
