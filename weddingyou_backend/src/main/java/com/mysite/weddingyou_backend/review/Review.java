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
	
	@Column(name = "review_img",length=2000)
    private String reviewImg;
	
	@Column(name = "review_text", nullable = false)
	private String reviewText;
	
	@Column(name = "review_stars", nullable = false)
	private int reviewStars;
	
	@Column(name = "review_date", nullable = false)
	private LocalDateTime reviewDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "user_email", referencedColumnName = "email", nullable = false)
	private UserLogin user;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "planner_email", referencedColumnName = "email", nullable = false)
	private PlannerLogin planner;
}
