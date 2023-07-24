package com.project.chamjimayo.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "review")
@Getter
@ToString(exclude = "reviewId")
@NoArgsConstructor
public class Review extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "review_id")
	private Long reviewId;

	// 회원 아이디 (해당 리뷰를 어떤 회원이 썼는가)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	// 화장실 아이디 (어떤 화장실에 대한 리뷰인가)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "restroom_id")
	private Restroom restroom;

	// 리뷰 내용
	@Column(name = "review_content")
	private String reviewContent;

	// 별점 (0 ~ 5점)
	@Column(name = "rating")
	private Integer rating;

	private Review(User user, Restroom restroom, String reviewContent, Integer rating) {
		this.user = user;
		this.restroom = restroom;
		this.reviewContent = reviewContent;
		this.rating = rating;
	}

	public static Review create(User user, Restroom restroom, String reviewContent, Integer rating) {
		return new Review(user, restroom, reviewContent, rating);
	}

	// 리뷰 수정을 위한 setter
	public void updateReview(String reviewContent, Integer rating) {
		this.reviewContent = reviewContent;
		this.rating = rating;
	}
}

