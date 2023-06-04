package com.mysite.weddingyou_backend.review;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysite.weddingyou_backend.comment.CommentDTO;
import com.mysite.weddingyou_backend.plannerLogin.PlannerLogin;
import com.mysite.weddingyou_backend.plannerLogin.PlannerLoginRepository;
import com.mysite.weddingyou_backend.userLogin.UserLogin;
import com.mysite.weddingyou_backend.userLogin.UserLoginRepository;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

	private final ReviewService reviewService;
	private final UserLoginRepository userLoginRepository;
	private final PlannerLoginRepository plannerLoginRepository;

	@Autowired
	public ReviewController(ReviewService reviewService, UserLoginRepository userLoginRepository,
			PlannerLoginRepository plannerLoginRepository) {
		this.reviewService = reviewService;
		this.userLoginRepository = userLoginRepository;
		this.plannerLoginRepository = plannerLoginRepository;
	}

	@PostMapping
	public ResponseEntity<ReviewDTO> createReview(@RequestParam("file") MultipartFile file,
			@RequestParam("reviewDTO") String reviewDTOJson, @RequestHeader("category") String category) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			ReviewDTO reviewDTO = objectMapper.readValue(reviewDTOJson, ReviewDTO.class);

			String email = reviewDTO.getReviewWriter();

			if (category.equals("user")) {
				UserLogin user = userLoginRepository.findByEmail(email);
				if (user != null) {
					reviewDTO.setReviewWriter(user.getName());
				}
			} else if (category.equals("planner")) {
				PlannerLogin planner = plannerLoginRepository.findByEmail(email);
				if (planner != null) {
					reviewDTO.setReviewWriter(planner.getName());
				}
			}

			String folderPath = "C:\\Project\\customerservice";
			String originalFileName = file.getOriginalFilename();
			String filePath = folderPath + "\\" + originalFileName;
			file.transferTo(new File(filePath));
			reviewDTO.setReviewImg(originalFileName);

			ReviewDTO savedReview = reviewService.createReview(reviewDTO);
			return new ResponseEntity<>(savedReview, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 리뷰수정
	@PutMapping("/{reviewId}")
	public ReviewDTO updateReview(@PathVariable Long reviewId, @RequestBody ReviewDTO reviewDTO) {
		return reviewService.updateReview(reviewId, reviewDTO);
	}

	// 리뷰삭제
	@DeleteMapping("/{reviewId}")
	public void deleteReview(@PathVariable Long reviewId) {
		reviewService.deleteReview(reviewId);
	}

	// 리뷰목록
	@GetMapping
	public List<ReviewDTO> getAllReviews() {
		return reviewService.getAllReviews();
	}

	// 리뷰검색(확인안됨)
	@GetMapping("/search/{keyword}")
	public List<ReviewDTO> searchReviews(@RequestParam String keyword) {
		return reviewService.searchReviews(keyword);
	}

	// 댓글작성
	@PostMapping("/{reviewId}/comments")
	public ResponseEntity<CommentDTO> createComment(@PathVariable Long reviewId, @RequestBody CommentDTO commentDTO) {
		CommentDTO newComment = reviewService.createComment(reviewId, commentDTO);
		return ResponseEntity.ok(newComment);
	}

	// 댓글수정
	@PutMapping("/{reviewId}/comments/{commentId}")
	public CommentDTO updateComment(@PathVariable Long commentId, @RequestBody CommentDTO commentDTO) {
		return reviewService.updateComment(commentId, commentDTO);
	}

	// 댓글삭제
	@DeleteMapping("/{reviewId}/comments/{commentId}")
	public void deleteComment(@PathVariable Long commentId) {
		reviewService.deleteComment(commentId);
	}

}