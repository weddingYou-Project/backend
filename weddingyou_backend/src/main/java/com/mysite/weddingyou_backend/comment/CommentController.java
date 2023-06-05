package com.mysite.weddingyou_backend.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.weddingyou_backend.review.Review;
import com.mysite.weddingyou_backend.review.ReviewRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CommentController {

	@Autowired
	public final CommentService commentService;
	
	@Autowired
	public final ReviewRepository reviewRepository;
	
	@PostMapping("/createcomment")
	public int createComments(@RequestParam Long estimateId, @RequestParam String email, @RequestParam String category,
			@RequestParam String commentContent){
		Review targetReview = reviewRepository.findByEstimateId(estimateId);
		Long reviewId = targetReview.getReviewId();
		CommentDTO commentdto = new CommentDTO();
		commentdto.setCommentContent(commentContent);
		int res = commentService.createComment(reviewId, commentdto, category, email);
		return res;
	}
}
