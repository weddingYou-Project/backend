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
    private Long itemId;

	@Column(name = "item_img")
    private String itemImg;
	
//	@ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private UserRegister userRegister;
	
	@Column(name="img_content",nullable=false)
	private String imgContent;
	
	@Column(name = "like_count", nullable = false)
    private int likeCount;
	
	@Column(name = "item_name", nullable = false)
    private String itemName;
	
	@Column(name = "item_write_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private LocalDateTime itemWriteDate;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "category1", nullable = false)
    private Category1 category1;

	@Enumerated(EnumType.STRING)
	@Column(name = "category2", nullable = false)
    private Category2 category2;
	
	public enum Category1 {
		웨딩홀, 사진영상, 스튜디오, 신혼여행, 드레스, 남성예복, 메이크업, 부케
	}
	
	public enum Category2 {
		웨딩홀, 사진영상, 스튜디오, 신혼여행, 드레스, 남성예복, 메이크업, 부케
	}
	
//	@OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Like> likes = new ArrayList<>();
}
