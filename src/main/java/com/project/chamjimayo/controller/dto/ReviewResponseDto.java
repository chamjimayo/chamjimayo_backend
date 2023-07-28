package com.project.chamjimayo.controller.dto;

import com.project.chamjimayo.domain.entity.Review;
import io.swagger.v3.oas.annotations.media.Schema;
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

	private ReviewResponseDto(Long reviewId, Long userId, Long restroomId, String reviewContent,
		Integer rating) {
		this.reviewId = reviewId;
		this.userId = userId;
		this.restroomId = restroomId;
		this.reviewContent = reviewContent;
		this.rating = rating;
	}

	public static ReviewResponseDto create(Long reviewId, Long userId, Long restroomId,
		String reviewContent, Integer rating) {
		return new ReviewResponseDto(reviewId, userId, restroomId, reviewContent, rating);
	}

	public static ReviewResponseDto fromEntity(Review review) {
		return new ReviewResponseDto(review.getReviewId(), review.getUser().getUserId(),
			review.getRestroom().getRestroomId(), review.getReviewContent(), review.getRating());
	}
}
