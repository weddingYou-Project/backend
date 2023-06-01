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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

	private final ReviewService reviewService;

	@Autowired
	public ReviewController(ReviewService reviewService) {
		this.reviewService = reviewService;
	}

	// 리뷰작성
	@PostMapping
	public ResponseEntity<ReviewDTO> createReview(@RequestParam("file") MultipartFile file,
			@RequestParam("reviewDTO") String reviewDTOJson, @RequestParam("writerName") String writerName) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			ReviewDTO reviewDTO = objectMapper.readValue(reviewDTOJson, ReviewDTO.class);
			reviewDTO.setWriterName(writerName);
			String folderPath = "C:\\Project\\customerservice";
			String filePath = folderPath + "\\" + file.getOriginalFilename();

			File folder = new File(folderPath);
			if (!folder.exists()) {
				folder.mkdirs(); // 폴더가 존재하지 않으면 폴더 생성
			}

			if (!file.isEmpty()) {
				File newFile = new File(filePath);
				file.transferTo(newFile);
				reviewDTO.setAttachment(filePath);
			}

			ReviewDTO newReview = reviewService.createReview(reviewDTO);
			return ResponseEntity.ok(newReview);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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