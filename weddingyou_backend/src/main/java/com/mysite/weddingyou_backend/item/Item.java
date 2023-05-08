package com.mysite.weddingyou_backend.item;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "item")
public class Item {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private int itemId;

	@Column(name = "item_img"
//			, nullable = false
			)
    private byte[] itemImg;
	
//	@ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private UserRegister userRegister;
	
	@Column(name = "like_count", nullable = false)
    private int likeCount;
	
	@Column(name = "item_name", nullable = false)
    private String itemName;
	
	@Column(name = "item_write_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private LocalDateTime itemWriteDate;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "category", nullable = false)
    private Category category;

	
	public enum Category {
		//웨딩홀
		일반, 호텔, 채플, 스몰, 야외, 전통혼례, 
		//스튜디오
		인물중심, 배경, 인물and배경,
		//의상
		한복, 드레스, 남성예복,
		//메이크업
		헤어, 피부, 색조,
		//신혼여행
		국내, 해외,
		//부케
	    라운드, 드롭, 케스케이드, 핸드타이드
	}
	
//	@OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Like> likes = new ArrayList<>();
}
