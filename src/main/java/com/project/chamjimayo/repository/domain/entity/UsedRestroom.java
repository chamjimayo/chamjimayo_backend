package com.project.chamjimayo.repository.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "used_restroom")
@Getter
@NoArgsConstructor
public class UsedRestroom extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @Column(name = "restroom_id")
  private Long restroomId;

  @Column(name = "is_reviewed")
  private boolean isReviewed;

  @Column(name = "review_id")
  private Long reviewId;

  @Builder
  public UsedRestroom(User user, long restroomId) {
    this.user = user;
    this.restroomId = restroomId;
    this.isReviewed = false;
    this.reviewId = (long) -1;
  }

  // 리뷰 등록 후 isReview true + reviewId 등록
  public void EnrollReview(Long reviewId) {
    this.reviewId = reviewId;
    this.isReviewed = true;
  }

  // 리뷰 삭제 후 isReview false + reviewId 삭제
  public void DeleteReview() {
    this.reviewId = (long) -1;
    this.isReviewed = false;
  }
}