package com.mysite.weddingyou_backend.userUpdateDelete;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController //데이터를 반환
public class UserUpdateDeleteController {
	
	@Autowired
	UserUpdateDeleteService service;
	

	 @PostMapping("/user/userSearch")
	 public UserUpdateDelete searchUser(@RequestBody UserUpdateDeleteDTO user) {
		 System.out.println(user.getEmail());
		 
		 	UserUpdateDelete searchedUser = service.getUserByEmail(user.getEmail());
		 	System.out.println("searchedUser: "+searchedUser.getPhoneNum());
		    return searchedUser;
	 }

	 @PostMapping("/user/userDelete")
	    public ResponseEntity<UserUpdateDelete> deleteUser(@RequestBody UserUpdateDeleteDTO user) {
		 UserUpdateDelete searchedUser = service.getUserByEmail(user.getEmail());
		service.delete(searchedUser);
		return ResponseEntity.status(HttpStatus.OK).build();
	    }
	 
	 @PostMapping("/user/userUpdate")
	    public UserUpdateDelete updateUser(@RequestBody UserUpdateDeleteDTO user) throws Exception {
		 UserUpdateDelete searchedUser = service.getUserByEmail(user.getPreemail());
		 UserUpdateDelete emailDuplicateUser = service.getUserByEmail(user.getEmail());
		 if(user.getPreemail().equals(user.getEmail()) || emailDuplicateUser==null) {
			 searchedUser.setEmail(user.getEmail());
			 searchedUser.setPassword(user.getPassword());
			 searchedUser.setPhoneNum(user.getPhoneNum());
			 searchedUser.setGender(user.getGender());
			service.save(searchedUser);
		 }else {
			 throw new Exception("이메일이 중복됩니다!");
		 }
		
		 return searchedUser;
		
	    }
	 
	 	

}