package com.project.chamjimayo.service;

import com.project.chamjimayo.controller.dto.response.ReviewResponse;
import com.project.chamjimayo.repository.RestroomJpaRepository;
import com.project.chamjimayo.repository.ReviewRepository;
import com.project.chamjimayo.repository.UserJpaRepository;
import com.project.chamjimayo.repository.domain.entity.Restroom;
import com.project.chamjimayo.repository.domain.entity.Review;
import com.project.chamjimayo.repository.domain.entity.User;
import com.project.chamjimayo.service.dto.ReviewDto;
import com.project.chamjimayo.service.exception.RestroomNotFoundException;
import com.project.chamjimayo.service.exception.ReviewNotFoundException;
import com.project.chamjimayo.service.exception.UserNotFoundException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

  private final ReviewRepository reviewRepository;
  private final RestroomJpaRepository restroomJpaRepository;
  private final UserJpaRepository userJpaRepository;

  /**
   * 리뷰 등록
   */
  public ReviewResponse createReview(Long userId, ReviewDto reviewDto) {
    Long restroomId = reviewDto.getRestroomId();
    String reviewContent = reviewDto.getReviewContent();
    Integer rating = reviewDto.getRating();

    User user = userJpaRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException("유저를 찾지 못했습니다. ID: " + userId));
    Restroom restroom = restroomJpaRepository.findById(restroomId)
        .orElseThrow(
            () -> new RestroomNotFoundException("해당 화장실을 찾을 수 없습니다. ID: " + restroomId));

    Review review = Review.create(user, restroom, reviewContent, rating);
    reviewRepository.save(review);

    averageRating(review.getRestroom().getRestroomId());

    return dtoFromEntity(review);
  }

  /**
   * 해당 리뷰 조회
   */
  public ReviewResponse getReview(Long reviewId) {
    Review review = findReviewById(reviewId);
    return dtoFromEntity(review);
  }

  /**
   * 리뷰 수정
   */
  public ReviewResponse updateReview(Long reviewId, ReviewDto reviewDto) {

    Review review = findReviewById(reviewId);

    String reviewContent = reviewDto.getReviewContent();
    Integer rating = reviewDto.getRating();

    review.updateReview(reviewContent, rating);
    Review updateReview = reviewRepository.save(review);

    averageRating(review.getRestroom().getRestroomId());

    return dtoFromEntity(updateReview);
  }

  /**
   * 리뷰 삭제
   */
  public void deleteReview(Long reviewId) {
    Review review = findReviewById(reviewId);
    Optional<Review> updateReview = reviewRepository.findById(reviewId);
    Long restroomId = updateReview.get().getRestroom().getRestroomId();
    reviewRepository.deleteById(reviewId);
    averageRating(restroomId);
  }

  /**
   * 해당 유저의 모든 리뷰 조회 (최신순)
   */
  public List<ReviewResponse> getReviewByUserID(Long userId) {
    Optional<User> user = userJpaRepository.findUserByUserId(userId);
    if (user.isEmpty()) {
      throw new UserNotFoundException("유저를 찾을 수 없습니다. ID: " + userId);
    }

    List<Review> allReviews = reviewRepository.findAllByUser(user);
    Collections.reverse(allReviews);
    return allReviews.stream()
        .map(this::dtoFromEntity)
        .collect(Collectors.toList());
  }

  /**
   * 해당 화장실의 모든 리뷰 조회 (최신순)
   */
  public List<ReviewResponse> getReviewsByRestroomId(Long restroomId) {
    Optional<Restroom> restroom = restroomJpaRepository.findById(restroomId);
    if (restroom.isEmpty()) {
      throw new RestroomNotFoundException("해당 화장실을 찾을 수 없습니다. ID: " + restroomId);
    }

    List<Review> allReviews = reviewRepository.findAllByRestroom(restroom);
    Collections.reverse(allReviews);
    return allReviews.stream()
        .map(this::dtoFromEntity)
        .collect(Collectors.toList());
  }

  /**
   * 해당 화장실의 모든 리뷰를 별점(rating)이 높은 순으로 정렬하여 조회
   */
  public List<ReviewResponse> getReviewsByRestroomIdOrderByHighRating(Long restroomId) {
    Optional<Restroom> restroom = restroomJpaRepository.findById(restroomId);
    if (restroom.isEmpty()) {
      throw new RestroomNotFoundException("해당 화장실을 찾을 수 없습니다. ID: " + restroomId);
    }
    List<Review> allReviews = reviewRepository.findAllByRestroom(restroom);
    return allReviews.stream()
        .sorted(Comparator.comparing(Review::getRating).reversed())
        .map(this::dtoFromEntity)
        .collect(Collectors.toList());
  }

  /**
   * 해당 화장실의 모든 리뷰를 별점(rating)이 낮은 순으로 정렬하여 조회
   */
  public List<ReviewResponse> getReviewsByRestroomIdOrderByLowRating(Long restroomId) {
    Optional<Restroom> restroom = restroomJpaRepository.findById(restroomId);
    if (restroom.isEmpty()) {
      throw new RestroomNotFoundException("해당 화장실을 찾을 수 없습니다. ID: " + restroomId);
    }
    List<Review> allReviews = reviewRepository.findAllByRestroom(restroom);
    return allReviews.stream()
        .sorted(Comparator.comparing(Review::getRating))
        .map(this::dtoFromEntity)
        .collect(Collectors.toList());
  }

  /**
   * 해당 화장실의 평균 평점 계산 (화장실이 없다면 0점 반환)
   */
  public void averageRating(Long restroomId) {
    Optional<Restroom> restroom = restroomJpaRepository.findById(restroomId);
    if (restroom.isEmpty()) {
      throw new RestroomNotFoundException("해당 화장실을 찾지 못했습니다. ID: " + restroomId);
    }
    // restroomId에 해당하는 review 리스트 반환
    List<Review> allReviews = reviewRepository.findAllByRestroom(restroom);
    float totalRating = 0;
    float count = 0;

    // 평균 평점 계산
    for (Review review : allReviews) {
      totalRating += review.getRating();
      count++;
    }
    float averageRating = count > 0 ? totalRating / count : 0;

    // 해당 restroom의 averageRating에 업데이트
    Restroom updateRestroom = restroom.get();
    updateRestroom.updateRating(averageRating);
  }

  /**
   * 리뷰에서 response 추출하기
   */
  public ReviewResponse dtoFromEntity(Review review) {
    User user = review.getUser();
    Restroom restroom = review.getRestroom();  // 리뷰와 연관된 화장실 정보 가져오기

    // 화장실 사진이 없으면 null 반환
    String restroomPhotoUrl = restroom.getRestroomPhotos().isEmpty()
        ? null : restroom.getRestroomPhotos().get(0).getPhotoUrl();

    return ReviewResponse.create(
        review.getReviewId(), user.getUserId(), user.getNickname(),
        user.getUserProfile(), restroom.getRestroomId(), restroomPhotoUrl,
        restroom.getRestroomName(), review.getReviewContent(), review.getRating(),
        review.getUpdatedDate()
    );
  }

  /**
   * 리뷰 찾기
   */
  public Review findReviewById(Long reviewId) {
    return reviewRepository.findById(reviewId)
        .orElseThrow(() -> new ReviewNotFoundException("리뷰를 찾지 못했습니다. ID: " + reviewId));
  }

}
