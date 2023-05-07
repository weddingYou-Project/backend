package com.mysite.weddingyou_backend.userUpdateDelete;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController //데이터를 반환
public class UserUpdateDeleteController {
	
	@Autowired
	UserUpdateDeleteService service;
	

	 @PostMapping("/user/userSearch")
	 public UserUpdateDelete searchUser(@RequestBody UserUpdateDeleteDTO user) {
		 System.out.println(user.getEmail());
		 
		 	UserUpdateDelete searchedUser = service.getUserByEmail(user.getEmail());
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
	 
	 @PostMapping("/user/updateprofileImg")
	 public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("useremail") String email) {
		    try {	
		    	UserUpdateDelete searchedUser = service.getUserByEmail(email);
		    	System.out.println(file.getContentType());
		    	System.out.println(file.getOriginalFilename());
		    	System.out.println(email);
		    	String path = "C:\\Project\\profileImg";
		    	File folder = new File(path);
		    	if(!folder.exists()) {
		    		try {
		    			folder.mkdir();
		    		}catch(Exception e) {
		    			e.getStackTrace();
		    		}
		    	}
		    	
		        Files.copy(file.getInputStream(), Paths.get("C:/Project/profileImg/", file.getOriginalFilename()),StandardCopyOption.REPLACE_EXISTING); //request에서 들어온 파일을 uploads 라는 경로에 originalfilename을 String 으로 올림
		        searchedUser.setUserImg(file.getOriginalFilename()); //searchedUser에다가 이미지 파일 이름 저장
		      //  searchedUser.setUserImgUrl(file);
		        service.save(searchedUser); // 이미지파일이름 데이터베이스에 업데이트함
		        System.out.println(searchedUser.getUserImg());
		        return ResponseEntity.ok().build();
		    } catch (IOException e) {
		        e.printStackTrace();
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		    }
		}
	 
	 @RequestMapping("/user/getprofileImg")
	 public ResponseEntity<byte[]> getImage(@RequestBody UserUpdateDeleteDTO user) {
		 System.out.println(user.getEmail());
		 UserUpdateDelete searchedUser = service.getUserByEmail(user.getEmail());
	     if (searchedUser != null) {
	         Path imagePath = Paths.get(searchedUser.getUserImg());
	         try {
	             byte[] imageBytes = Files.readAllBytes(imagePath);
	             return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
	         } catch (IOException e) {
	             e.printStackTrace();
	             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	         }
	     } else {
	         return ResponseEntity.notFound().build();
	     }
	 }
	 
	 	

}