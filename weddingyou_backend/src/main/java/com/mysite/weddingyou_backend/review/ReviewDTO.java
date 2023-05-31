package com.mysite.weddingyou_backend.review;

import java.time.LocalDateTime;

import com.mysite.weddingyou_backend.plannerLogin.PlannerLogin;
import com.mysite.weddingyou_backend.userLogin.UserLogin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDTO {

	//id
	private long reviewId;

	//작성자
    private UserLogin user;
    
    private PlannerLogin planner;


	//제목
	private String reviewTitle;

	//내용
	private String reviewContent;

	//첨부파일
	private String reviewImg;

	//작성일
	private LocalDateTime reviewWriteDate;
	
    public void setAttachment(String reviewImg) {
        this.reviewImg = reviewImg;
    }
	
	public static ReviewDTO fromEntity(Review review) {
		ReviewDTO reviewDTO = new ReviewDTO();
		reviewDTO.setReviewId(review.getReviewId());
        reviewDTO.setUser(review.getUser());
        reviewDTO.setPlanner(review.getPlanner());
		reviewDTO.setReviewTitle(review.getReviewTitle());
		reviewDTO.setReviewContent(review.getReviewContent());
		reviewDTO.setReviewImg(review.getReviewImg());
		reviewDTO.setReviewWriteDate(review.getReviewWriteDate());

		return reviewDTO;
	}
}
