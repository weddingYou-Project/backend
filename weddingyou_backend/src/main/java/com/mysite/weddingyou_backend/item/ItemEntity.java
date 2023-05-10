package com.mysite.weddingyou_backend.item;

import java.time.LocalDateTime;

import com.mysite.weddingyou_backend.like.LikeEntity;
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
@Table(name = "item")
public class ItemEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long itemId;

	@Column(name = "item_img")
    private String itemImg;
	
	@Column(name="img_content",nullable=false)
	private String imgContent;
	
	@Column(name = "item_name", nullable = false)
    private String itemName;
	
	@Column(name = "item_write_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private LocalDateTime itemWriteDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "like")
	private LikeEntity likeCount;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "category1", nullable = false)
    private Category1 category1;

	@Enumerated(EnumType.STRING)
	@Column(name = "category2", nullable = false)
    private Category2 category2;
	
	public enum Category1 {
		웨딩홀, 의상, 스튜디오, 메이크업, 신혼여행, 부케
	}
	
	public enum Category2 {
		일반, 호텔, 채플, 스몰, 야외, 전통혼례, 한복, 드레스, 남성예복, 인물중심, 배경중심, 균형적인, 헤어, 메이크업, 국내, 해외, 라운드, 드롭, 케스케이드, 핸드타이드
	}

}