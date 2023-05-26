package com.mysite.weddingyou_backend.mypageAdmin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.weddingyou_backend.plannerLogin.PlannerLogin;
import com.mysite.weddingyou_backend.plannerLogin.PlannerLoginRepository;
import com.mysite.weddingyou_backend.userLogin.UserLogin;
import com.mysite.weddingyou_backend.userLogin.UserLoginRepository;

@RestController
@RequestMapping("/mypageAdmin")
public class MypageAdminController {
	
	@Autowired
    MypageAdminService mypageAdminService;
	
	@Autowired
    MypageAdminRepository mypageAdminRepository;
	
	@Autowired
	UserLoginRepository userLoginRepository;
	
	@Autowired
	PlannerLoginRepository plannerLoginRepository;
	

	//전체 사용자 정보 리스트 조회
	@GetMapping("/all")
	public ResponseEntity<Page<MypageAdmin>> getAllUsersAndPlanners(
			@RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size) {
		
		//페이징 기능
		Pageable pageable = PageRequest.of(page, size);
		
	    List<UserLogin> users = userLoginRepository.findAll();
	    List<PlannerLogin> planners = plannerLoginRepository.findAll();

	    List<MypageAdmin> mypageAdmins = new ArrayList<>();

	    for (UserLogin user : users) {
	        // 이미 저장된 사용자인지 확인
	        if (!mypageAdminRepository.existsByUserEmail(user.getEmail())) {
	            MypageAdmin userMypageAdmin = new MypageAdmin();
	            userMypageAdmin.setType("user");
	            userMypageAdmin.setUserEmail(user.getEmail());
	            userMypageAdmin.setUserName(user.getName());
	            userMypageAdmin.setUserPassword(user.getPassword());
	            userMypageAdmin.setUserGender(user.getGender());
	            userMypageAdmin.setUserPhoneNum(user.getPhoneNum());
	            userMypageAdmin.setUserJoinDate(user.getUserJoinDate());

	            mypageAdmins.add(userMypageAdmin); //userMypageAdmin객체를 mypageAdmins 리스트에 추가하는 역할
	            mypageAdminRepository.save(userMypageAdmin); //db에 저장하는 역할
	        }
	    }

	    for (PlannerLogin planner : planners) {
	        // 이미 저장된 플래너인지 확인
	        if (!mypageAdminRepository.existsByPlannerEmail(planner.getEmail())) {
	            MypageAdmin plannerMypageAdmin = new MypageAdmin();
	            plannerMypageAdmin.setType("planner");
	            plannerMypageAdmin.setPlannerEmail(planner.getEmail());
	            plannerMypageAdmin.setPlannerName(planner.getName());
	            plannerMypageAdmin.setPlannerPassword(planner.getPassword());
	            plannerMypageAdmin.setPlannerGender(planner.getGender());
	            plannerMypageAdmin.setPlannerPhoneNum(planner.getPhoneNum());
	            plannerMypageAdmin.setPlannerJoinDate(planner.getPlannerJoinDate());

	            mypageAdmins.add(plannerMypageAdmin);
	            mypageAdminRepository.save(plannerMypageAdmin);
	        }
	    }
	    
	    Page<MypageAdmin> mypageAdminPage = mypageAdminRepository.findAll(pageable);
	    
	    if (!mypageAdminPage.isEmpty()) {
	        return ResponseEntity.ok().body(mypageAdminPage);
	    } else {
	        // 사용자 또는 플래너 정보가 없는 경우에 대한 처리
	        return ResponseEntity.noContent().build();
	    }  
	    
	}
	
	//사용자 정보 수정
	@PostMapping("/modify")
	public int updateUser(@RequestBody MypageAdmin mypageAdmin) {
		int update = 0;
		
		//mypageAdmin 테이블의 회원정보 업데이트
		update = mypageAdminService.updateUser(mypageAdmin.getAdminId(), mypageAdmin.getUserName(), 
				mypageAdmin.getUserPassword(), mypageAdmin.getUserPhoneNum());
		
		// user 테이블도 업데이트
        userLoginRepository.updateUserByEmail(mypageAdmin.getUserEmail(), mypageAdmin.getUserName(), 
                mypageAdmin.getUserPassword(), mypageAdmin.getUserPhoneNum());
		
		return update;
	}
	
	//사용자 정보 삭제

	
	//사용자 정보 검색

}
