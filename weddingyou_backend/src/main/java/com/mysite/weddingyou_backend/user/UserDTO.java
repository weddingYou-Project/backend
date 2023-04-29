package com.mysite.weddingyou_backend.user;

import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

	private int userId;

	private String name;

	@Email
	private String email;

	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")
	private String password;

	@Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$")
	private String phoneNum;

	private byte[] userImg;

	private LocalDateTime userJoinDate;
}
