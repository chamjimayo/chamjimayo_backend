package com.project.chamjimayo.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ReviewDto {
	@Schema(type = "Long", example = "1")
	private final Long userId;
	@Schema(type = "Long", example = "1")
	private final Long restroomId;
	@Schema(type = "string", example = "깔끔해요!")
	private final String reviewContent;
	@Schema(type = "Float", example = "4.3")
	private final Float rating;

	private ReviewDto(Long userId, Long restroomId, String reviewContent, Float rating) {
		this.userId = userId;
		this.restroomId = restroomId;
		this.reviewContent = reviewContent;
		this.rating = rating;
	}

	public static ReviewDto create(Long userId, Long restroomId, String reviewContent, Float rating) {
		return new ReviewDto(userId, restroomId, reviewContent, rating);
	}

}
