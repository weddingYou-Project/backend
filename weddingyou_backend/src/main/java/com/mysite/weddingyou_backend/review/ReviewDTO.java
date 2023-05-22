package com.mysite.weddingyou_backend.review;

import javax.validation.constraints.NotNull;

import com.mysite.weddingyou_backend.plannerLogin.PlannerLogin;
import com.mysite.weddingyou_backend.userLogin.UserLogin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDTO {
	
	@NotNull
	private Long reviewId;
	
	@NotNull
	private UserLogin userEmail;
	
	@NotNull
    private PlannerLogin plannerEmail;

	@NotNull
	private String reviewText;

	@NotNull
	private int reviewStars;
	
	private String reviewImg;

}
