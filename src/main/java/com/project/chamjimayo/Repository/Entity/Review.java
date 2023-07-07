package com.project.chamjimayo.Repository.Entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "review")
@Getter
@ToString(exclude = "review_id")
@NoArgsConstructor
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "review_id")
	private Integer reviewId;

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
	@Min(0)
	@Max(5)
	@Column(name = "rating")
	private Float rating;

	// 생성일
	@Column(name = "created_date")
	private LocalDateTime createdDate;

	// 수정일
	@Column(name = "updated_date")
	private LocalDateTime updatedDate;

	// 리뷰 상태
	@Pattern(regexp = "[01]")
	@Column(name = "status")
	private boolean status;
}

