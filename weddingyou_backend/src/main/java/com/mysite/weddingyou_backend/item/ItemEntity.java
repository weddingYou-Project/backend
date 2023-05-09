package com.mysite.weddingyou_backend.item;

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

@Entity
@Setter
@Getter
@Table(name = "item")
public class ItemEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "item_id")
	private Long itemId;
	
	@Column(name = "item_img", nullable = true)
	private byte[] itemImg;
	
	@Column(name = "user_id", nullable = false)
	private String userId;
	
	@Column(name = "like", nullable = false)
	private Integer likeCount;

	@Column(name = "planner_id", nullable = false)
	private String plannerId;

	@Column(name = "item_name", nullable = false)
	private String itemName;

	@Column(name = "category", nullable = false)
	private String category;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "planner_id", referencedColumnName = "planner_id", insertable = false, updatable = false) //외래키 정의
	private PlannerLogin planner;
	//db에 쓰여질 수 없음, 참조만 가능
	//referenceColumnName : 참조하는 테이블의 기본키 컬럼 이름을 나타냄

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
	private UserLogin user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "email")
	private UserLogin email;
	
	// 위치 정보를 위한 필드
    @Column(name = "latitude")
    private Double latitude; // 위도

    @Column(name = "longitude")
    private Double longitude; // 경도

}
