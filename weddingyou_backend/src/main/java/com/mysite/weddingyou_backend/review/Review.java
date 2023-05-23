package com.mysite.weddingyou_backend.review;

import java.time.LocalDateTime;

import com.mysite.weddingyou_backend.plannerLogin.PlannerLogin;
import com.mysite.weddingyou_backend.userLogin.UserLogin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "review")
public class Review {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;
	
	@Column(name = "review_img", nullable = true)
    private String reviewImg;
	
	@Column(name = "review_text", nullable = false)
	private String reviewText;
	
	@Column(name = "review_stars", nullable = false)
	private int reviewStars;
	
	@Column(name = "review_date", nullable = false)
	private LocalDateTime reviewDate;
	
	@Column(name = "user_email", nullable = false)
	private String userEmail;

	@Column(name = "planner_email", nullable = false)
	private String plannerEmail;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_email", referencedColumnName = "email", insertable = false, updatable = false, nullable = false)
	private UserLogin userLogin;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "planner_email", referencedColumnName = "email", insertable = false, updatable = false, nullable = false)
	private PlannerLogin plannerLogin;

}
