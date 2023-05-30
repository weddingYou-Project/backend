package com.mysite.weddingyou_backend.review;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class ReviewService {

	private final ReviewRepository reviewRepository;

	public ReviewService(ReviewRepository reviewRepository) {
		this.reviewRepository = reviewRepository;
	}

	public ReviewDTO createReview(ReviewDTO reviewDTO) {
		Review review = new Review();
		review.setReviewImg(reviewDTO.getReviewImg());
		review.setReviewTitle(reviewDTO.getReviewTitle());
		review.setReviewContent(reviewDTO.getReviewContent());
		review.setReviewWriteDate(LocalDateTime.now());
		Review savedReview = reviewRepository.save(review);
		return ReviewDTO.fromEntity(savedReview);
	}

	public ReviewDTO updateReview(Long reviewId, ReviewDTO reviewDTO) {
		Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new IllegalArgumentException("불러오기 실패"));
		review.setReviewImg(reviewDTO.getReviewImg());
		review.setReviewTitle(reviewDTO.getReviewTitle());
		review.setReviewContent(reviewDTO.getReviewContent());
		review.setReviewWriteDate(LocalDateTime.now());
		Review updatedReview = reviewRepository.save(review);
		return ReviewDTO.fromEntity(updatedReview);
	}

	public void deleteReview(Long reviewId) {
		reviewRepository.deleteById(reviewId);
	}

	public ReviewDTO getReviewById(Long reviewId) {
		Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new IllegalArgumentException("불러오기 실패"));
		return ReviewDTO.fromEntity(review);
	}

	public List<ReviewDTO> searchReviews(String keyword) {
		List<Review> reviews = reviewRepository.findByReviewTitleContaining(keyword);
		return reviews.stream().map(ReviewDTO::fromEntity).collect(Collectors.toList());
	}
}
