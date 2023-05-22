package com.mysite.weddingyou_backend.review;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mysite.weddingyou_backend.plannerLogin.PlannerLogin;
import com.mysite.weddingyou_backend.userLogin.UserLogin;

import jakarta.persistence.EntityManager;


@RestController
@RequestMapping("/reviews")
public class ReviewController {
	
	@Autowired
	ReviewService reviewService;
	
	@PostMapping
	public Review createReview(@RequestBody ReviewDTO reviewDTO,
			@RequestParam("file") MultipartFile file) throws IOException {
		
		UserLogin userEmail = reviewDTO.getUserEmail();
	    PlannerLogin plannerEmail = reviewDTO.getPlannerEmail();
	    // userEmail과 plannerEmail을 사용하여 리뷰 작성 및 처리
	    
	    // 리뷰 생성 및 데이터베이스 저장
	    Review createdReview = reviewService.createReview(reviewDTO, file);
	    
		
//	    // 플래너 정보를 사용하여 작업 수행
//	    PlannerLogin planner = entityManager.find(PlannerLogin.class, plannerEmail);
//	    // 사용자 정보 추가
//	    Review reviewUser = reviewService.getUserEmail(userEmail); // 로그인한 사용자의 email를 가져옴
	    
	    // 파일 저장
	 	if (!file.isEmpty()) {
	 		String fileName = file.getOriginalFilename();
	 		String filePath = "c:\\Project\\upload";
	 		File destFile = new File(filePath, fileName);
	 		file.transferTo(destFile); // 파일을 지정된 경로에 저장
	 		// 파일 경로를 리뷰 객체에 저장
	 		createdReview.setReviewImg(destFile.getAbsolutePath());
	 	}
	    return createdReview;
	}

}
