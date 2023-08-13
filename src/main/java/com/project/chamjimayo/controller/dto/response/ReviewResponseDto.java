package com.project.chamjimayo.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ReviewResponseDto {

  @Schema(type = "Long", example = "1")
  private Long reviewId;

  @Schema(type = "Long", example = "1")
  private Long userId;

  @Schema(type = "string", example = "nickname")
  private String nickname;

  @Schema(type = "string", example = "https://example.com/profile.jpg")
  private String userProfile;

  @Schema(type = "Long", example = "1")
  private Long restroomId;

  @Schema(type = "string", example = "깔끔해요!")
  private String reviewContent;

  @Schema(type = "Integer", example = "4")
  private Integer rating;

  @Schema(type = "string", example = "23.07.29")
  @JsonFormat(pattern = "yy.MM.dd")
  private LocalDateTime dateTime;

  private ReviewResponseDto(Long reviewId, Long userId, String nickname, String userProfile,
      Long restroomId, String reviewContent,
      Integer rating, LocalDateTime dateTime) {
    this.reviewId = reviewId;
    this.userId = userId;
    this.nickname = nickname;
    this.userProfile = userProfile;
    this.restroomId = restroomId;
    this.reviewContent = reviewContent;
    this.rating = rating;
    this.dateTime = dateTime;
  }

  public static ReviewResponseDto create(Long reviewId, Long userId, String nickname,
      String userProfile, Long restroomId, String reviewContent, Integer rating,
      LocalDateTime dateTime) {
    return new ReviewResponseDto(reviewId, userId, nickname, userProfile, restroomId, reviewContent,
        rating, dateTime);
  }
}
