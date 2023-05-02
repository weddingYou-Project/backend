package com.mysite.weddingyou_backend.planner;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity // 물리적인 테이블을 생성
@Setter
@Getter
@Table(name = "planner") //PlannerEntity클래스를 사용해서 user라는 테이블이 만들어짐
public class PlannerEntity {
	@Id // pk 지정
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "planner_id")
	private int plannerId;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "phone_number", nullable = false)
	private String phoneNum;

	@Column(name = "planner_img", nullable = true)
	private byte[] plannerImg;
	
	@Column(name = "planner_career_years", nullable = false)
	private String plannerCareerYears;

	@Column(name = "planner_join_date", columnDefinition = "datetime default current_timestamp")
	private LocalDateTime plannerJoinDate;
	

}
