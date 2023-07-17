package com.project.chamjimayo.service;

import com.project.chamjimayo.controller.dto.ReviewDto;
import com.project.chamjimayo.controller.dto.UpdateReviewDto;
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
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
	private final ReviewRepository reviewRepository;
	private final RestroomRepository restroomRepository;
	private final UserRepository userRepository;

	/**
	 * review 작성
	 */
	public ReviewDto createReview(ReviewDto reviewDto) {
		Long userId = reviewDto.getUserId();
		Long restroomId = reviewDto.getRestroomId();

		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException("유저를 찾지 못했습니다. ID: " + userId));
		Restroom restroom = restroomRepository.findById(restroomId)
			.orElseThrow(() -> new RestroomNotFoundException("해당 화장실을 찾을 수 없습니다. ID: " + restroomId));

		Review review = new Review(user, restroom, reviewDto.getReviewContent(), reviewDto.getRating());
		Review savedReview = reviewRepository.save(review);
		return ReviewDto.create(savedReview.getUser().getUserId(), savedReview.getRestroom().getRestroomId(),
			savedReview.getReviewContent(), savedReview.getRating());
	}

	/**
	 * review 조회
	 */
	public ReviewDto getReview(Long reviewId) {
		Review review = reviewRepository.findById(reviewId)
			.orElseThrow(() -> new ReviewNotFoundException("리뷰를 찾지 못했습니다. ID: " + reviewId));

		return ReviewDto.create(review.getUser().getUserId(), review.getRestroom().getRestroomId(),
			review.getReviewContent(), review.getRating());
	}

	/**
	 * review 수정 (유저 id, 화장실 id는 수정 불가)
	 */
	public ReviewDto updateReview(Long reviewId, UpdateReviewDto updateReviewDto) {
		Review review = reviewRepository.findById(reviewId)
			.orElseThrow(() -> new ReviewNotFoundException("리뷰를 찾지 못했습니다. ID: " + reviewId));

		review.UpdateReview(updateReviewDto.getReviewContent(), updateReviewDto.getRating());
		Review updatedReview = reviewRepository.save(review);

		return ReviewDto.create(updatedReview.getUser().getUserId(), updatedReview.getRestroom().getRestroomId(),
			updatedReview.getReviewContent(), updatedReview.getRating());
	}

	/**
	 * review 제거
	 */
	public void deleteReview(Long reviewId) {
		boolean reviewExist = reviewRepository.existsById(reviewId);
		if (!reviewExist) {
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
			.map(review -> ReviewDto.create(review.getUser().getUserId(), review.getRestroom().getRestroomId(),
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
			.map(review -> ReviewDto.create(review.getUser().getUserId(), review.getRestroom().getRestroomId(),
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
			.map(review -> ReviewDto.create(review.getUser().getUserId(), review.getRestroom().getRestroomId(),
				review.getReviewContent(), review.getRating()))
			.sorted(Comparator.comparing(ReviewDto::getRating))
			.collect(Collectors.toList());
	}

	/**
	 * 해당 화장실의 평균 평점 계산
	 * 화장실이 없다면 0점 반환
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
