package com.mysite.weddingyou_backend.plannerUpdateDelete;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.weddingyou_backend.userUpdateDelete.UserUpdateDelete;
import com.mysite.weddingyou_backend.userUpdateDelete.UserUpdateDeleteDTO;

@RestController //데이터를 반환
public class PlannerUpdateDeleteController {
	
	@Autowired
	PlannerUpdateDeleteService service;
	
	//회원 조회
	 @PostMapping("/planner/plannerSearch")
	    public PlannerUpdateDelete searchUser(@RequestBody PlannerUpdateDeleteDTO planner) {
	        PlannerUpdateDelete searchedPlanner = service.getPlannerByEmail(planner.getEmail());
	        System.out.println("career:" + searchedPlanner.getPlannerCareerYears());
	        return searchedPlanner;
	    }
	 
	 //회원 탈퇴
	 @PostMapping("/planner/plannerDelete")
	    public ResponseEntity<PlannerUpdateDelete> deleteUser(@RequestBody PlannerUpdateDeleteDTO planner) {
		 PlannerUpdateDelete searchedPlanner = service.getPlannerByEmail(planner.getEmail());
		service.delete(searchedPlanner);
		return ResponseEntity.status(HttpStatus.OK).build();
	    }
	 
	 //회원 업데이트
	 @PostMapping("/planner/userUpdate")
	    public PlannerUpdateDelete updateUser(@RequestBody PlannerUpdateDeleteDTO planner) throws Exception {
		 System.out.println(planner.getPreemail());
		 System.out.println(planner.getEmail());
		 PlannerUpdateDelete searchedPlanner = service.getPlannerByEmail(planner.getPreemail());
		 PlannerUpdateDelete emailDuplicatePlanner = service.getPlannerByEmail(planner.getEmail());
		 if(emailDuplicatePlanner==null) {
			 searchedPlanner.setEmail(planner.getEmail());
			 searchedPlanner.setPassword(planner.getPassword());
			 searchedPlanner.setPhoneNum(planner.getPhoneNum());
			 searchedPlanner.setGender(planner.getGender());
			 searchedPlanner.setPlannerCareerYears(planner.getCareer());
			 service.save(searchedPlanner);
		 }else {
			 throw new Exception("이메일이 중복됩니다!");
		 }
		
		return searchedPlanner;
	   }


}