package com.mysite.weddingyou_backend.comment;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mysite.weddingyou_backend.plannerLogin.PlannerLogin;
import com.mysite.weddingyou_backend.plannerLogin.PlannerLoginRepository;
import com.mysite.weddingyou_backend.review.Review;
import com.mysite.weddingyou_backend.review.ReviewRepository;
import com.mysite.weddingyou_backend.userLogin.UserLogin;
import com.mysite.weddingyou_backend.userLogin.UserLoginRepository;

@Service
public class CommentService {

	private final CommentRepository commentRepository;
	private final UserLoginRepository userLoginRepository;
	private final PlannerLoginRepository plannerLoginRepository;
	private final ReviewRepository reviewRepository;

	public CommentService(CommentRepository commentRepository, UserLoginRepository userLoginRepository,
			PlannerLoginRepository plannerLoginRepository, ReviewRepository reviewRepository) {
		this.commentRepository = commentRepository;
		this.userLoginRepository = userLoginRepository;
		this.plannerLoginRepository = plannerLoginRepository;
		this.reviewRepository = reviewRepository;
	}

	public int createComment(Long reviewId, CommentDTO commentDTO, String category, String email) {
	//	String category = (String) session.getAttribute("category");
	//	String email = (String) session.getAttribute("email");
		int res = 0;
		Comment comment = new Comment();
		comment.setCommentContent(commentDTO.getCommentContent());
		comment.setCommentDate(LocalDateTime.now());
		if ("user".equals(category)) {
			UserLogin user = userLoginRepository.findByEmail(email);
			if (user != null) {
				comment.setCommentWriter(user.getName());
			}
		} else if ("planner".equals(category)) {
			PlannerLogin planner = plannerLoginRepository.findByEmail(email);
			if (planner != null) {
				comment.setCommentWriter(planner.getName());
			}
		}
		Optional<Review> review = reviewRepository.findById(reviewId);
		Review targetReview = review.orElse(null);
		comment.setReview(targetReview);

		commentRepository.save(comment);
		res = 1;
		return res;
	}
}