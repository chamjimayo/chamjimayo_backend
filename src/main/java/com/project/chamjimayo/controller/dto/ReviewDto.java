package com.project.chamjimayo.controller.dto;

import com.project.chamjimayo.domain.entity.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ReviewDto {

	@Schema(type = "string", example = "깔끔해요!")
	private String reviewContent;
	@Schema(type = "Integer", example = "4")
	private Integer rating;

	private ReviewDto(String reviewContent, Integer rating) {
		this.reviewContent = reviewContent;
		this.rating = rating;
	}

	public static ReviewDto create(String reviewContent, Integer rating) {
		return new ReviewDto(reviewContent, rating);
	}

	public static ReviewDto fromEntity(Review review) {
		return new ReviewDto(review.getReviewContent(), review.getRating());
	}

}
