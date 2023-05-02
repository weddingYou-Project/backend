package com.mysite.weddingyou_backend.user;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@RestController //데이터를 반환
public class UserController {
	
	@Autowired
	UserService service;
	
	//로그인
	@PostMapping("/user/login")
	public UserEntity login(@RequestBody UserEntity userEntity, HttpSession session) { 
		System.out.println(userEntity);
		UserEntity loginResult = service.login(userEntity); //loginResult에는 dto가 저장됨
		if(loginResult != null) {
			//로그인 성공
//			session.setAttribute("loginEmail", loginResult.getEmail());		 
			return loginResult;
		}else {
			//로그인 실패
			return null;
		}
	}

	//비밀번호 수정
	@PostMapping("/user/updatePassword") //userDTO에 email과 password를 저장한다고 가정
	public int updatePassword(@RequestBody UserEntity userEntity, HttpSession session) { //리액트에서 email과 password란 이름으로 전달하도록
		int res = 0;
		res = service.updatePassword(userEntity.getEmail(), userEntity.getPassword());
		return res; //수정이 됐음1, 아니면 0
	}

}