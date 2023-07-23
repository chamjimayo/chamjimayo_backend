package com.project.chamjimayo.service;

import com.project.chamjimayo.controller.dto.ReviewDto;
import com.project.chamjimayo.controller.dto.ReviewRequestDto;
import com.project.chamjimayo.domain.entity.Restroom;
import com.project.chamjimayo.domain.entity.Review;
import com.project.chamjimayo.domain.entity.User;
import com.project.chamjimayo.exception.RestroomNotFoundException;
import com.project.chamjimayo.exception.ReviewNotFoundException;
import com.project.chamjimayo.exception.UserNotFoundException;
import com.project.chamjimayo.repository.RestroomJpaRepository;
import com.project.chamjimayo.repository.ReviewRepository;
import com.project.chamjimayo.repository.UserJpaRepository;
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
public class ReviewService {

	private final ReviewRepository reviewRepository;
	private final RestroomJpaRepository restroomJpaRepository;
	private final UserJpaRepository userJpaRepository;

	/**
	 * 리뷰 등록
	 */
	@Transactional
	public ReviewDto createReview(Long userId, ReviewRequestDto reviewRequestDto) {
		Long restroomId = reviewRequestDto.getRestroomId();
		String reviewContent = reviewRequestDto.getReviewContent();
		Float rating = reviewRequestDto.getRating();
		User user = userJpaRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException("유저를 찾지 못했습니다. ID: " + userId));
		Restroom restroom = restroomJpaRepository.findById(restroomId)
			.orElseThrow(
				() -> new RestroomNotFoundException("해당 화장실을 찾을 수 없습니다. ID: " + restroomId));

		Review review = Review.create(user, restroom, reviewContent, rating);
		reviewRepository.save(review);

		averageRating(review.getRestroom().getRestroomId());

		return ReviewDto.fromEntity(review);
	}

	/**
	 * 해당 리뷰 조회
	 */
	public ReviewDto getReview(Long reviewId) {
		Review review = reviewRepository.findById(reviewId)
			.orElseThrow(() -> new ReviewNotFoundException("리뷰를 찾지 못했습니다. ID: " + reviewId));
		return ReviewDto.fromEntity(review);
	}

	/**
	 * 리뷰 수정
	 */
	@Transactional
	public ReviewDto updateReview(Review review, ReviewDto reviewDto) {

		String reviewContent = reviewDto.getReviewContent();
		Float rating = reviewDto.getRating();
		review.updateReview(reviewContent, rating);
		reviewRepository.save(review);

		averageRating(review.getRestroom().getRestroomId());

		return ReviewDto.fromEntity(review);
	}

	/**
	 * 리뷰 삭제
	 */
	@Transactional
	public void deleteReview(Review review) {
		Long reviewId = review.getReviewId();
		reviewRepository.deleteById(reviewId);

		Optional<Review> updateReview = reviewRepository.findById(reviewId);
		Long restroomId = updateReview.get().getRestroom().getRestroomId();
		averageRating(restroomId);
	}

	/**
	 * 해당 화장실의 모든 리뷰 조회 (최신순)
	 */
	public List<ReviewDto> getReviewsByRestroomId(Long restroomId) {
		Optional<Restroom> restroom = restroomJpaRepository.findById(restroomId);
		if (restroom.isEmpty()) {
			throw new RestroomNotFoundException("해당 화장실을 찾을 수 없습니다. ID: " + restroomId);
		}

		List<Review> allReviews = reviewRepository.findAllByRestroom(restroom);
		List<ReviewDto> reversedReviews = allReviews.stream()
			.map(review -> ReviewDto.create(
				review.getReviewContent(), review.getRating()
			))
			.collect(Collectors.toList());

		Collections.reverse(reversedReviews);
		return reversedReviews;
	}

	/**
	 * 해당 화장실의 모든 리뷰를 별점(rating)이 높은 순으로 정렬하여 조회
	 */
	public List<ReviewDto> getReviewsByRestroomIdOrderByHighRating(Long restroomId) {
		Optional<Restroom> restroom = restroomJpaRepository.findById(restroomId);
		if (restroom.isEmpty()) {
			throw new RestroomNotFoundException("해당 화장실을 찾을 수 없습니다. ID: " + restroomId);
		}
		List<Review> allReviews = reviewRepository.findAllByRestroom(restroom);
		return allReviews.stream()
			.map(review -> ReviewDto.create(
				review.getReviewContent(), review.getRating()))
			.sorted(Comparator.comparing(ReviewDto::getRating).reversed())
			.collect(Collectors.toList());
	}

	/**
	 * 해당 화장실의 모든 리뷰를 별점(rating)이 낮은 순으로 정렬하여 조회
	 */
	public List<ReviewDto> getReviewsByRestroomIdOrderByLowRating(Long restroomId) {
		Optional<Restroom> restroom = restroomJpaRepository.findById(restroomId);
		if (restroom.isEmpty()) {
			throw new RestroomNotFoundException("해당 화장실을 찾을 수 없습니다. ID: " + restroomId);
		}
		List<Review> allReviews = reviewRepository.findAllByRestroom(restroom);
		return allReviews.stream()
			.map(review -> ReviewDto.create(
				review.getReviewContent(), review.getRating()))
			.sorted(Comparator.comparing(ReviewDto::getRating))
			.collect(Collectors.toList());
	}

	/**
	 * 해당 화장실의 평균 평점 계산 (화장실이 없다면 0점 반환)
	 */
	public void averageRating(Long restroomId) {
		Optional<Restroom> restroom = restroomJpaRepository.findById(restroomId);
		if (restroom.isEmpty()) {
			throw new ReviewNotFoundException("해당 화장실을 찾지 못했습니다. ID: " + restroomId);
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
}
