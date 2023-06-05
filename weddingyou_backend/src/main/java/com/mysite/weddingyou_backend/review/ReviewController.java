package com.mysite.weddingyou_backend.review;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mysite.weddingyou_backend.plannerUpdateDelete.PlannerUpdateDelete;
import com.mysite.weddingyou_backend.plannerUpdateDelete.PlannerUpdateDeleteRepository;

import jakarta.transaction.Transactional;


@RestController

public class ReviewController {
	
	@Autowired
	ReviewService reviewService;
	
	@Autowired
	PlannerUpdateDeleteRepository plannerUpdateDeleteRepository;
	
	@Autowired
	ReviewRepository reviewRepository;
	
	@Value("${spring.servlet.multipart.location}")
    String uploadDir;
	
	@PostMapping(value = "/reviews")
	public int createReview(@RequestParam("reviewText") String reviewText,
	        @RequestParam("reviewStars") Integer reviewStars,
	        @RequestParam(value = "reviewImg", required = false) MultipartFile[] reviewImg,
	        @RequestParam("userEmail") String userEmail,
	        @RequestParam("plannerEmail") String plannerEmail,
	        @RequestParam("estimateId") Long estimateId) throws IOException {
		
		int res = 0;
	    
	    // 파일 저장
		List<String> list = new ArrayList<>();
	 	if (!(reviewImg == null)) {
	 	for (MultipartFile file : reviewImg) {
	 		if (!file.isEmpty()) {
	 			File storedFilename = new File(UUID.randomUUID().toString() + "_" + file.getOriginalFilename());
	 			list.add("\"" + storedFilename.toString() + "\"");
                file.transferTo(storedFilename); //업로드
	 		}
	 	}
	 	}
	 	
	 	Review review = new Review();
	 	if(reviewText.equals("undefined")) {
	 		review.setReviewText("");
	 	}else {
	 		review.setReviewText(reviewText);
	 	}
	 	
	 	review.setReviewStars(reviewStars);
	 	review.setReviewImg(list.toString());
	 	review.setUserEmail(userEmail);
	 	review.setPlannerEmail(plannerEmail);
	 	review.setReviewDate(LocalDateTime.now());
	 	review.setEstimateId(estimateId);
	 	PlannerUpdateDelete plannerData = plannerUpdateDeleteRepository.findByEmail(plannerEmail);
	 	review.setReviewTitle(plannerData.getName()+" 플래너 Review");
	 	review.setReviewCounts(0);
	 	System.out.println(review);
	 	
	 	if(reviewService.findEstimate(estimateId)!=null) {
	 		Review targetReview = reviewService.findEstimate(estimateId);
	 		targetReview.setPlannerEmail(plannerEmail);
	 		targetReview.setReviewText(reviewText);
	 		targetReview.setReviewStars(reviewStars);
	 		targetReview.setReviewImg(list.toString());
	 		targetReview.setUserEmail(userEmail);
	 		targetReview.setReviewDate(LocalDateTime.now());
	 		reviewService.save(targetReview);
	 		res =1;
	 	}else {
	 	// 리뷰 생성 및 데이터베이스 저장
		    reviewService.save(review);
		    res=1;
	 	}
	 	
	 	
	    
	    
	    return res;
	    
	}
	
	@RequestMapping(value = "/getreviewslist")
	public List<Review> getReviews() {
		
		List<Review> reviewList = reviewService.getReviewList();
		return reviewList;
 
	    
	}
	
	@GetMapping(value="/estimateIdReview/{estimateId}")
	public Review getReviewByEstimateId(@PathVariable Long estimateId) {
		
		Review targetReview  = reviewService.findEstimate(estimateId);
		if(targetReview ==null) {
			
		}
		return targetReview;
	}
	
	@PutMapping(value="/reviewcount/{estimateId}")
	public Review addReviewCount(@PathVariable Long estimateId) {
		
		Review targetReview  = reviewService.findEstimate(estimateId);
		if(targetReview !=null) {
			int reviewCount = targetReview.getReviewCounts();
			targetReview.setReviewCounts(reviewCount+1);
			reviewRepository.save(targetReview);
		}
		return targetReview;
	}
	
	
	@RequestMapping("/review/imageview")
    public ResponseEntity<UrlResource> downloadReviewImg(@RequestParam("image") String stored) throws MalformedURLException {
        UrlResource resource = new UrlResource("file:" + uploadDir + "/" + stored);
        return ResponseEntity.ok().body(resource);
    }

	@Transactional
	@DeleteMapping("/review/delete/{estimateId}")
    public Review removeReview(@PathVariable Long estimateId)  {
		Review targetReview  = reviewService.findEstimate(estimateId);
		if(targetReview !=null) {
			reviewRepository.deleteByEstimateId(estimateId);
		}
		return targetReview;
    }
	
	@PostMapping(value = "/updatedreviews")
	public int updateReview(@RequestParam("reviewText") String reviewText,
			@RequestParam("reviewTitle") String reviewTitle,
	        @RequestParam("reviewStars") Integer reviewStars,
	        @RequestParam(value = "reviewImg", required = false) MultipartFile[] reviewImg,
	        @RequestParam("userEmail") String userEmail,
	        @RequestParam("plannerEmail") String plannerEmail,
	        @RequestParam("estimateId") Long estimateId) throws IOException {
		
		int res = 0;
	    
	    // 파일 저장
		List<String> list = new ArrayList<>();
	 	if (!(reviewImg == null)) {
	 	for (MultipartFile file : reviewImg) {
	 		if (!file.isEmpty()) {
	 			File storedFilename = new File(UUID.randomUUID().toString() + "_" + file.getOriginalFilename());
	 			list.add("\"" + storedFilename.toString() + "\"");
                file.transferTo(storedFilename); //업로드
	 		}
	 	}
	 	}
	 	
	 	Review review = new Review();
	 	if(reviewText.equals("undefined")) {
	 		review.setReviewText("");
	 	}else {
	 		review.setReviewText(reviewText);
	 	}
	 	
	 	review.setReviewStars(reviewStars);
	 	review.setReviewImg(list.toString());
	 	review.setUserEmail(userEmail);
	 	review.setPlannerEmail(plannerEmail);
	 	review.setReviewDate(LocalDateTime.now());
	 	review.setReviewTitle(reviewTitle);
	 	review.setEstimateId(estimateId);
	 	PlannerUpdateDelete plannerData = plannerUpdateDeleteRepository.findByEmail(plannerEmail);
	 	review.setReviewTitle(plannerData.getName()+"플래너 Review");
	 	review.setReviewCounts(0);
	 	System.out.println(review);
	 	
	 	if(reviewService.findEstimate(estimateId)!=null) {
	 		Review targetReview = reviewService.findEstimate(estimateId);
	 		targetReview.setPlannerEmail(plannerEmail);
	 		targetReview.setReviewText(reviewText);
	 		targetReview.setReviewStars(reviewStars);
	 		targetReview.setReviewImg(list.toString());
	 		targetReview.setUserEmail(userEmail);
	 		targetReview.setReviewDate(LocalDateTime.now());
	 		targetReview.setReviewTitle(reviewTitle);
	 		reviewService.save(targetReview);
	 		res =1;
	 	}else {
	 	// 리뷰 생성 및 데이터베이스 저장
		    reviewService.save(review);
		    res=1;
	 	}
	 	
	 	
	    
	    
	    return res;
	    
	}
	
	@PostMapping(value = "/reviewauthority/{estimateId}")
	public int checkUpdateDeleteAuthorityReview(
	        @RequestParam("userEmail") String userEmail,
	        @PathVariable("estimateId") Long estimateId) throws IOException {
		int res =0;
		Review targetReview  = reviewService.findEstimate(estimateId);
		if(targetReview !=null) {
			if(targetReview.getUserEmail().equals(userEmail)) {
				res =1;
			}else {
				res =0;
			}
		}
		return res;
	    
	}
}