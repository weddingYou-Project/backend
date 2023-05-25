package com.mysite.weddingyou_backend.mypageAdmin;

import java.time.LocalDateTime;

import com.mysite.weddingyou_backend.plannerLogin.PlannerLogin;
import com.mysite.weddingyou_backend.plannerRegister.PlannerRegister.Gender;
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
	
	@Column(name = "UsersType")
	private String type;
	
//	public void setType(String type) {
//        this.type = type;
//    }
	
	//user
	@Column(name = "user_email")
	private String userEmail;
	
	@Column(name = "user_name")
	private String userName;
	
	@Column(name = "user_password")
	private String userPassword;
	
	@Column(name = "user_gender")
	private Gender userGender;
	
	@Column(name = "user_phoneNum")
	private String userPhoneNum;
	
	@Column(name = "user_join_date")
	private LocalDateTime userJoinDate;
	
	//planner
	@Column(name = "planner_email")
	private String plannerEmail;
	
	@Column(name = "planner_name")
	private String plannerName;
	
	@Column(name = "planner_password")
	private String plannerPassword;
	
	@Column(name = "planner_gender")
	private Gender plannerGender;
	
	@Column(name = "planner_phoneNum")
	private String plannerPhoneNum;
	
	@Column(name = "planner_join_date")
	private LocalDateTime plannerJoinDate;
	
	// user 테이블
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_email", referencedColumnName="email", insertable = false, updatable = false, nullable = false)
    private UserLogin userE;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_name", referencedColumnName="name", insertable = false, updatable = false, nullable = false)
    private UserLogin userN;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_password", referencedColumnName="password", insertable = false, updatable = false, nullable = false)
    private UserLogin userPW;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_gender", referencedColumnName="gender", insertable = false, updatable = false, nullable = false)
    private UserLogin userG;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_phoneNum", referencedColumnName="phone_number", insertable = false, updatable = false, nullable = false)
    private UserLogin userPhone;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_join_date", referencedColumnName="user_join_date", insertable = false, updatable = false, nullable = false)
    private UserLogin userJoin;
    
	// planner 테이블
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "planner_email", referencedColumnName="email", insertable = false, updatable = false, nullable = false)
    private PlannerLogin plannerE;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "planner_name", referencedColumnName="name", insertable = false, updatable = false, nullable = false)
    private PlannerLogin plannerN;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "planner_password", referencedColumnName="password", insertable = false, updatable = false, nullable = false)
    private PlannerLogin plannerPW;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "planner_gender", referencedColumnName="gender", insertable = false, updatable = false, nullable = false)
    private PlannerLogin plannerG;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "planner_phoneNum", referencedColumnName="phone_number", insertable = false, updatable = false, nullable = false)
    private PlannerLogin plannerPhone;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "planner_join_date", referencedColumnName="planner_join_date", insertable = false, updatable = false, nullable = false)
    private PlannerLogin plannerJoin;

	public UserLogin getUserLogin() {
		return null;
	}
	
	public PlannerLogin getPlannerLogin() {
		return null;
	}

    
//    @ManyToOne(fetch=FetchType.LAZY)
//    @JoinColumn(name = "estimate_id", referencedColumnName="e_id")
//    private Estimate estimateId;
	
}
