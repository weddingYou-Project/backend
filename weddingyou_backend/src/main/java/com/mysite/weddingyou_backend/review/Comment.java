package com.mysite.weddingyou_backend.review;

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
@Table(name = "Comment")
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id")
	private Long commentId;
	
	// 작성자
	// 유저 (외래 키)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "useremail", referencedColumnName = "email")
	private UserLogin user;
	
	// 플래너 (외래 키)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "planneremail", referencedColumnName = "email")
	private PlannerLogin planner;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "review_id")
	private Review review;

	@Column(name = "comment_content")
	private String commentContent;
	

}
