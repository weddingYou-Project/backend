package com.mysite.weddingyou_backend.mypageAdmin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mysite.weddingyou_backend.plannerLogin.PlannerLogin;
import com.mysite.weddingyou_backend.plannerLogin.PlannerLoginRepository;
import com.mysite.weddingyou_backend.userLogin.UserLogin;
import com.mysite.weddingyou_backend.userLogin.UserLoginRepository;

@Controller
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
	public ResponseEntity<List<MypageAdmin>> getAllUsersAndPlanners() {
	    List<UserLogin> users = userLoginRepository.findAll();
	    List<PlannerLogin> planners = plannerLoginRepository.findAll();

	    List<MypageAdmin> mypageAdmins = new ArrayList<>();

	    for (UserLogin user : users) {
	        // 이미 저장된 사용자인지 확인
	        if (!mypageAdminRepository.existsByUserEmail(user.getEmail())) {
	            MypageAdmin userMypageAdmin = new MypageAdmin();
	            userMypageAdmin.setType("user");
	            userMypageAdmin.setUserEmail(user.getEmail());

	            mypageAdmins.add(userMypageAdmin);
	            mypageAdminRepository.save(userMypageAdmin);
	        }
	    }

	    for (PlannerLogin planner : planners) {
	        // 이미 저장된 플래너인지 확인
	        if (!mypageAdminRepository.existsByPlannerEmail(planner.getEmail())) {
	            MypageAdmin plannerMypageAdmin = new MypageAdmin();
	            plannerMypageAdmin.setType("planner");
	            plannerMypageAdmin.setPlannerEmail(planner.getEmail());

	            mypageAdmins.add(plannerMypageAdmin);
	            mypageAdminRepository.save(plannerMypageAdmin);
	        }
	    }

	    if (!mypageAdmins.isEmpty()) {
	        return ResponseEntity.ok().body(mypageAdmins);
	    } else {
	        // 사용자 또는 플래너 정보가 없는 경우에 대한 처리
	        return ResponseEntity.notFound().build();
	    }   
	}
}
