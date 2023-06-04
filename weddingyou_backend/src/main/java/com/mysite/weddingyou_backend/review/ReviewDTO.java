package com.mysite.weddingyou_backend.review;

import java.time.LocalDateTime;
import java.util.List;

import com.mysite.weddingyou_backend.comment.CommentDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDTO {

	private long reviewId;

	//작성자
	private String reviewWriter;
    
    // 별점
    private double starRating;
    
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
        reviewDTO.setReviewWriter(review.getReviewWriter());
        reviewDTO.setStarRating(review.getStarRating());
		reviewDTO.setReviewTitle(review.getReviewTitle());
		reviewDTO.setReviewContent(review.getReviewContent());
		reviewDTO.setReviewImg(review.getReviewImg());
		reviewDTO.setReviewWriteDate(review.getReviewWriteDate());

		return reviewDTO;
	}
	
    private List<CommentDTO> comments;

}
