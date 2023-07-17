package com.project.chamjimayo.controller.dto;

import com.project.chamjimayo.domain.entity.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ReviewDto {
	@Schema(type = "Long", example = "1")
	private final Long userId;
	@Schema(type = "Long", example = "1")
	private final Long restroomId;
	@Schema(type = "string", example = "깔끔해요!")
	private String reviewContent;
	@Schema(type = "Float", example = "4.3")
	private Float rating;

	private ReviewDto(Long userId, Long restroomId, String reviewContent, Float rating) {
		this.userId = userId;
		this.restroomId = restroomId;
		this.reviewContent = reviewContent;
		this.rating = rating;
	}

	public static ReviewDto create(Long userId, Long restroomId, String reviewContent, Float rating) {
		return new ReviewDto(userId, restroomId, reviewContent, rating);
	}

	public static ReviewDto fromEntity(Review review) {
		return new ReviewDto(review.getUser().getUserId(), review.getRestroom().getRestroomId(), review.getReviewContent(), review.getRating());
	}

}
