package com.project.chamjimayo.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UpdateReviewDto {
	@Schema(type = "string", example = "깔끔해요!")
	private final String reviewContent;
	@Schema(type = "Float", example = "4.3")
	private final Float rating;

	private UpdateReviewDto(String reviewContent, Float rating) {
		this.reviewContent = reviewContent;
		this.rating = rating;
	}

	public static UpdateReviewDto create(String reviewContent, Float rating) {
		return new UpdateReviewDto(reviewContent, rating);
	}

}
