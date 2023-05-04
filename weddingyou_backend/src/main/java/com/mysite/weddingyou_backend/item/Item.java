package com.mysite.weddingyou_backend.item;

import java.util.ArrayList;
import java.util.List;

import com.mysite.weddingyou_backend.like.Like;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

	@Column(name = "item_img", nullable = false)
    private byte[] itemImg;
	
	@Column(name = "user_id", nullable = false)
    private int userId;
	
	@OneToMany
	@Column(name = "like_count", nullable = false)
    private int likeCount;
	
	@Column(name = "item_name", nullable = false)
    private String itemName;
	
	@Column(name = "category", nullable = false)
    private String category;
	
	@OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> like = new ArrayList<>();
}
