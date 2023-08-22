package com.project.chamjimayo.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ReviewResponse {

  @Schema(type = "Long", example = "1")
  private Long reviewId;

  @Schema(type = "Long", example = "1")
  private Long userId;

  @Schema(type = "string", example = "닉네임")
  private String nickname;

  @Schema(type = "string", example = "https://example.com/userProfile.jpg")
  private String userProfile;

  @Schema(type = "Long", example = "1")
  private Long restroomId;

  @Schema(type = "string", example = "https://example.com/restroomImg.jpg")
  private String restroomPhotoUrl;

  @Schema(type = "string", example = "화장실 이름")
  private String restroomName;

  @Schema(type = "string", example = "깔끔해요!")
  private String reviewContent;

  @Schema(type = "Integer", example = "4")
  private Integer rating;

  @Schema(type = "string", example = "8월 22일")
  @JsonFormat(pattern = "M'월' d'일'")
  private LocalDateTime dateTime;

  private ReviewResponse(Long reviewId, Long userId, String nickname, String userProfile,
      Long restroomId, String restroomPhotoUrl, String restroomName, String reviewContent,
      Integer rating, LocalDateTime dateTime) {
    this.reviewId = reviewId;
    this.userId = userId;
    this.nickname = nickname;
    this.userProfile = userProfile;
    this.restroomId = restroomId;
    this.reviewContent = reviewContent;
    this.rating = rating;
    this.dateTime = dateTime;
    this.restroomPhotoUrl = restroomPhotoUrl;
    this.restroomName = restroomName;
  }

  public static ReviewResponse create(Long reviewId, Long userId, String nickname,
      String userProfile, Long restroomId, String restroomPhotoUrl, String restroomName,
      String reviewContent, Integer rating, LocalDateTime dateTime) {
    return new ReviewResponse(reviewId, userId, nickname, userProfile, restroomId, restroomPhotoUrl,
        restroomName, reviewContent, rating, dateTime);
  }
}
