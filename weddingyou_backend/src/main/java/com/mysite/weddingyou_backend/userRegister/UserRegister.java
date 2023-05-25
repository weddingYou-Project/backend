package com.mysite.weddingyou_backend.userRegister;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

import com.mysite.weddingyou_backend.item.Item;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "user")
public class UserRegister {
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone_number", nullable = false)
    private String phoneNum;
    
//    @Column(name = "user_img")
//    private byte[] userImg;
    
    @Enumerated(EnumType.STRING) // Enum 값을 String 형태로 저장
    private Gender gender;

    @Column(name = "user_join_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private LocalDateTime userJoinDate;
    
    public enum Gender {
    	male,
    	female
    }
}

