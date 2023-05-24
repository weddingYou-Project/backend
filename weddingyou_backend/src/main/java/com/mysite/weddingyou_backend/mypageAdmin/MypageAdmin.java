package com.mysite.weddingyou_backend.mypageAdmin;

import com.mysite.weddingyou_backend.estimate.Estimate;
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
@Table(name = "mypageAdmin")
public class MypageAdmin {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
	private Long adminId;
	
	@Column(name = "Type", nullable = false)
	private String type;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_email", referencedColumnName="email")
    private UserLogin user;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "planner_email", referencedColumnName="email")
    private PlannerLogin planner;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "estimate_id", referencedColumnName="e_id")
    private Estimate estimateId;
	
}
