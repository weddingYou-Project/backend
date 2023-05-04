package com.mysite.weddingyou_backend.plannerUpdateDelete;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //데이터를 반환
public class PlannerUpdateDeleteController {
	
	@Autowired
	PlannerUpdateDeleteService service;
	
	//회원 조회
	 @PostMapping("/plannerSearch")
	    public ResponseEntity<PlannerUpdateDelete> searchUser(@Valid String email) {
	        PlannerUpdateDelete searchedPlanner = service.getPlannerByEmail(email);
	        return ResponseEntity.status(HttpStatus.CREATED).body(searchedPlanner);
	    }


}