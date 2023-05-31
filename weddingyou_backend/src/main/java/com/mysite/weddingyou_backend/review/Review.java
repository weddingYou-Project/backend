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
@Table(name = "Review")
public class Review {

	//id
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private long reviewId;

	//작성자
    // 유저 (외래 키)
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "useremail", referencedColumnName="email")
    private UserLogin user;

    // 플래너 (외래 키)
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "planneremail", referencedColumnName="email")
    private PlannerLogin planner;

	//제목
	@Column(name = "review_title")
    private String reviewTitle;

	//내용
	@Column(name = "review_content")
    private String reviewContent;

	//첨부파일
	@Column(name = "review_attachedfile")
    private String reviewImg;

	//작성일
	@Column(name = "review_write_date")
    private LocalDateTime reviewWriteDate;

}
