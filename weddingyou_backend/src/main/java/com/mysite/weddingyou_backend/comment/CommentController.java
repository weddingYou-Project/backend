package com.mysite.weddingyou_backend.comment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.weddingyou_backend.review.Review;
import com.mysite.weddingyou_backend.review.ReviewRepository;

import jakarta.transaction.Transactional;
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
	
	@PostMapping("/updatecomment")
	public List<Comment> updateComment(@RequestParam int index, @RequestParam Long estimateId, @RequestParam String commentContent){
		Review targetReview = reviewRepository.findByEstimateId(estimateId);
		Long reviewId = targetReview.getReviewId();
		return commentService.updateComments(index, targetReview, commentContent);
	}
	
	@Transactional
	@PostMapping("/deletecomment")
	public List<Comment> deletecomment(@RequestParam int index, @RequestParam Long estimateId){
		Review targetReview = reviewRepository.findByEstimateId(estimateId);
		Long reviewId = targetReview.getReviewId();
		return commentService.deleteComments(index, targetReview);
	}
	
//	@PostMapping("/getcomment")
//	public List<Comment> getComments(@RequestParam Long estimateId){
//		Review targetReview = reviewRepository.findByEstimateId(estimateId);
//		Long reviewId = targetReview.getReviewId();
//		System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=" + reviewId);
//		List<Comment> commentData = commentService.getComments(reviewId);
//	
//		return commentData;
//	}
}
