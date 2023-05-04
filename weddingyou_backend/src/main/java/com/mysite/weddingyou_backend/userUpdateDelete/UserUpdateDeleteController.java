package com.mysite.weddingyou_backend.userUpdateDelete;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //데이터를 반환
public class UserUpdateDeleteController {
	
	@Autowired
	UserUpdateDeleteService service;
	

	 @PostMapping("user/userSearch")
		    public ResponseEntity<UserUpdateDelete> searchUser(@Valid String email) {
		 UserUpdateDelete searchedUser = service.getUserByEmail(email);
		        return ResponseEntity.status(HttpStatus.CREATED).body(searchedUser);
		    }

	 @PostMapping("/user/userDelete")
	    public ResponseEntity<UserUpdateDelete> deleteUser(@Valid String email) {
		 UserUpdateDelete searchedUser = service.getUserByEmail(email);
		service.delete(searchedUser);
		return ResponseEntity.status(HttpStatus.OK).build();
	    }

}