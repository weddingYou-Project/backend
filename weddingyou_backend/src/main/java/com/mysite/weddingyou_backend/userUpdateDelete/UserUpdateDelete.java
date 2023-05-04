package com.mysite.weddingyou_backend.userUpdateDelete;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity // 물리적인 테이블을 생성
@Setter
@Getter
@Table(name = "user") //UserEntity클래스를 사용해서 user라는 테이블이 만들어짐
public class UserUpdateDelete {
	@Id // pk 지정
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int userId;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "phone_number", nullable = false)
	private String phoneNum;

	@Column(name = "user_img", nullable = true)
	private byte[] userImg;

	@Column(name = "user_join_date", columnDefinition = "datetime default current_timestamp")
	private LocalDateTime userJoinDate;
	

}