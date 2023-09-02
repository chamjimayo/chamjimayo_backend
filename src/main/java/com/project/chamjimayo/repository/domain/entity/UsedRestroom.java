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

  @Builder
  public UsedRestroom(User user, long restroomId, boolean isReviewed) {
    this.user = user;
    this.restroomId = restroomId;
    this.isReviewed = isReviewed;
  }

  // 리뷰 등록 후 isReview 변경을 위한 setter
  public void EnrollReview(boolean isReviewed) {
    this.isReviewed = isReviewed;
  }
}