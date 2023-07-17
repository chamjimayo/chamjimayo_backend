package com.project.chamjimayo.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ReviewRequestDto {

	@Schema(type = "Long", example = "1")
	private final Long restroomId;
	@Schema(type = "string", example = "깔끔해요!")
	private final String reviewContent;
	@Schema(type = "Float", example = "4.3")
	private final Float rating;

	private ReviewRequestDto(Long restroomId, String reviewContent, Float rating) {
		this.restroomId = restroomId;
		this.reviewContent = reviewContent;
		this.rating = rating;
	}

	public static ReviewRequestDto create(Long restroomId, String reviewContent, Float rating) {
		return new ReviewRequestDto(restroomId, reviewContent, rating);
	}
}
