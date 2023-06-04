package com.mysite.weddingyou_backend.review;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mysite.weddingyou_backend.comment.Comment;
import com.mysite.weddingyou_backend.comment.CommentDTO;
import com.mysite.weddingyou_backend.comment.CommentRepository;
import com.mysite.weddingyou_backend.userLogin.UserLogin;
import com.mysite.weddingyou_backend.userLogin.UserLoginRepository;

@Service
public class ReviewService {

	private final ReviewRepository reviewRepository;
	private final CommentRepository commentRepository;
	private final UserLoginRepository userLoginRepository;


	public ReviewService(ReviewRepository reviewRepository, CommentRepository commentRepository, UserLoginRepository userLoginRepository) {
		this.reviewRepository = reviewRepository;
		this.commentRepository = commentRepository;
		this.userLoginRepository = userLoginRepository;
	}

	public ReviewDTO createReview(ReviewDTO reviewDTO) {
		Review review = new Review();
		review.setReviewImg(reviewDTO.getReviewImg());
		review.setStarRating(reviewDTO.getStarRating());
		review.setReviewTitle(reviewDTO.getReviewTitle());
		review.setReviewContent(reviewDTO.getReviewContent());
		review.setReviewWriteDate(LocalDateTime.now());
		
		String email = reviewDTO.getReviewWriter();
		UserLogin user = userLoginRepository.findByEmail(email);
		if (user != null) {
			review.setReviewWriter(user.getName());
		}
		Review savedReview = reviewRepository.save(review);
		return ReviewDTO.fromEntity(savedReview);
	}

	public ReviewDTO updateReview(Long reviewId, ReviewDTO reviewDTO) {
		Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new IllegalArgumentException("불러오기 실패"));
		review.setReviewImg(reviewDTO.getReviewImg());
		review.setStarRating(reviewDTO.getStarRating());
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

	public List<ReviewDTO> getAllReviews() {
		List<Review> reviews = reviewRepository.findAll();
		return reviews.stream().map(ReviewDTO::fromEntity).collect(Collectors.toList());

	}

	// 댓글
	public CommentDTO createComment(Long reviewId, CommentDTO commentDTO) {
		Review review = reviewRepository.findById(reviewId)
				.orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));

		Comment comment = new Comment();
		comment.setCommentContent(commentDTO.getCommentContent());
		comment.setReview(review);

		review.getComments().add(comment);
		commentRepository.save(comment);

		return CommentDTO.fromEntity(comment);
	}

	public CommentDTO updateComment(Long commentId, CommentDTO commentDTO) {
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

		comment.setCommentContent(commentDTO.getCommentContent());

		commentRepository.save(comment);

		return CommentDTO.fromEntity(comment);
	}

	public void deleteComment(Long commentId) {
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

		commentRepository.delete(comment);
	}
}
