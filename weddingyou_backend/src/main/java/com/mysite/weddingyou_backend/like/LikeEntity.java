package com.mysite.weddingyou_backend.like;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mysite.weddingyou_backend.item.Item;
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
@Table(name = "like")
public class LikeEntity  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long likeId;
    
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
	
   
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "email", referencedColumnName="email")
    private UserLogin user;
	
    @Column(name = "like", nullable = false)
    private Integer likeCount;
    
    @Column(name = "location")
    private String location;
}

