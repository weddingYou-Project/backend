package com.mysite.weddingyou_backend.mypageAdmin;

import com.mysite.weddingyou_backend.estimate.Estimate;
import com.mysite.weddingyou_backend.plannerLogin.PlannerLogin;
import com.mysite.weddingyou_backend.userLogin.UserLogin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
	
	@Column(name = "UsersType")
	private String type;
	
//	public void setType(String type) {
//        this.type = type;
//    }
	
	@Column(name = "user_email"/*, nullable = false*/)
	private String userEmail;

	@Column(name = "planner_email"/*, nullable = false*/)
	private String plannerEmail;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_email", referencedColumnName="email", insertable = false, updatable = false, nullable = false)
    private UserLogin user;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "planner_email", referencedColumnName="email", insertable = false, updatable = false, nullable = false)
    private PlannerLogin planner;

    
//    @ManyToOne(fetch=FetchType.LAZY)
//    @JoinColumn(name = "estimate_id", referencedColumnName="e_id")
//    private Estimate estimateId;
	
}
