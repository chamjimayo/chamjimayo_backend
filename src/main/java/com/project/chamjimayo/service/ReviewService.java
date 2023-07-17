package com.project.chamjimayo.service;

import com.project.chamjimayo.controller.dto.ReviewDto;
import com.project.chamjimayo.controller.dto.ReviewRequestDto;
import com.project.chamjimayo.domain.entity.Restroom;
import com.project.chamjimayo.domain.entity.Review;
import com.project.chamjimayo.domain.entity.User;
import com.project.chamjimayo.exception.RestroomNotFoundException;
import com.project.chamjimayo.exception.ReviewNotFoundException;
import com.project.chamjimayo.exception.UserNotFoundException;
import com.project.chamjimayo.repository.RestroomRepository;
import com.project.chamjimayo.repository.ReviewRepository;
import com.project.chamjimayo.repository.UserRepository;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

	private final ReviewRepository reviewRepository;
	private final RestroomRepository restroomRepository;
	private final UserRepository userRepository;

	/**
	 * 리뷰 등록
	 */
	public ReviewDto createReview(Long userId, ReviewRequestDto reviewRequestDto) {
		Long restroomId = reviewRequestDto.getRestroomId();
		String reviewContent = reviewRequestDto.getReviewContent();
		Float rating = reviewRequestDto.getRating();
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException("유저를 찾지 못했습니다. ID: " + userId));
		Restroom restroom = restroomRepository.findById(restroomId)
			.orElseThrow(
				() -> new RestroomNotFoundException("해당 화장실을 찾을 수 없습니다. ID: " + restroomId));

		Review review = new Review(user, restroom, reviewContent, rating);
		reviewRepository.save(review);

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
	public ReviewDto updateReview(Long reviewId, ReviewDto reviewDto) {
		Review review = reviewRepository.findById(reviewId)
			.orElseThrow(() -> new ReviewNotFoundException("리뷰를 찾지 못했습니다. ID: " + reviewId));

		String reviewContent = reviewDto.getReviewContent();
		Float rating = reviewDto.getRating();
		review.updateReview(reviewContent, rating);
		reviewRepository.save(review);

		return ReviewDto.fromEntity(review);
	}

	/**
	 * 리뷰 삭제
	 */
	public void deleteReview(Long reviewId) {
		boolean reviewExists = reviewRepository.existsById(reviewId);
		if (!reviewExists) {
			throw new ReviewNotFoundException("리뷰를 찾을 수 없습니다. ID: " + reviewId);
		}
		reviewRepository.deleteById(reviewId);
	}

	/**
	 * 해당 화장실의 모든 리뷰 조회
	 */
	public List<ReviewDto> getReviewsByRestroomId(Long restroomId) {
		Optional<Restroom> restroom = restroomRepository.findById(restroomId);
		if (restroom.isEmpty()) {
			throw new RestroomNotFoundException("해당 화장실을 찾을 수 없습니다. ID: " + restroomId);
		}
		List<Review> allReviews = reviewRepository.findAllByRestroom(restroom);
		return allReviews.stream()
			.map(review -> ReviewDto.create(review.getUser().getUserId(),
				review.getRestroom().getRestroomId(),
				review.getReviewContent(), review.getRating()))
			.collect(Collectors.toList());
	}

	/**
	 * 해당 화장실의 모든 리뷰를 별점(rating)이 높은 순으로 정렬하여 조회
	 */
	public List<ReviewDto> getReviewsByRestroomIdOrderByHighRating(Long restroomId) {
		Optional<Restroom> restroom = restroomRepository.findById(restroomId);
		if (restroom.isEmpty()) {
			throw new RestroomNotFoundException("해당 화장실을 찾을 수 없습니다. ID: " + restroomId);
		}
		List<Review> allReviews = reviewRepository.findAllByRestroom(restroom);
		return allReviews.stream()
			.map(review -> ReviewDto.create(review.getUser().getUserId(),
				review.getRestroom().getRestroomId(),
				review.getReviewContent(), review.getRating()))
			.sorted(Comparator.comparing(ReviewDto::getRating).reversed())
			.collect(Collectors.toList());
	}

	/**
	 * 해당 화장실의 모든 리뷰를 별점(rating)이 낮은 순으로 정렬하여 조회
	 */
	public List<ReviewDto> getReviewsByRestroomIdOrderByLowRating(Long restroomId) {
		Optional<Restroom> restroom = restroomRepository.findById(restroomId);
		if (restroom.isEmpty()) {
			throw new RestroomNotFoundException("해당 화장실을 찾을 수 없습니다. ID: " + restroomId);
		}
		List<Review> allReviews = reviewRepository.findAllByRestroom(restroom);
		return allReviews.stream()
			.map(review -> ReviewDto.create(review.getUser().getUserId(),
				review.getRestroom().getRestroomId(),
				review.getReviewContent(), review.getRating()))
			.sorted(Comparator.comparing(ReviewDto::getRating))
			.collect(Collectors.toList());
	}

	/**
	 * 해당 화장실의 평균 평점 계산 (화장실이 없다면 0점 반환)
	 */
	public Float averageRating(Long restroomId) {
		Optional<Restroom> restroom = restroomRepository.findById(restroomId);
		if (restroom.isEmpty()) {
			throw new ReviewNotFoundException("해당 화장실을 찾지 못했습니다. ID: " + restroomId);
		}
		// restroomId에 해당하는 review 리스트 반환
		List<Review> allReviews = reviewRepository.findAllByRestroom(restroom);
		float totalRating = 0;
		int count = 0;

		for (Review review : allReviews) {
			totalRating += review.getRating();
			count++;
		}

		return count > 0 ? totalRating / count : 0;
	}
}
