package com.mysite.weddingyou_backend.review;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mysite.weddingyou_backend.plannerLogin.PlannerLogin;
import com.mysite.weddingyou_backend.userLogin.UserLogin;


@RestController

public class ReviewController {
	
	@Autowired
	ReviewService reviewService;
	
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
	 	review.setReviewText(reviewText);
	 	review.setReviewStars(reviewStars);
	 	review.setReviewImg(list.toString());
	 	review.setUserEmail(userEmail);
	 	review.setPlannerEmail(plannerEmail);
	 	review.setReviewDate(LocalDateTime.now());
	 	review.setEstimateId(estimateId);
	 	
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

}