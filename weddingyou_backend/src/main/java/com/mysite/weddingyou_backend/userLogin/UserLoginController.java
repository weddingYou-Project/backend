package com.mysite.weddingyou_backend.userLogin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.weddingyou_backend.plannerLogin.PlannerLogin;
import com.mysite.weddingyou_backend.plannerLogin.PlannerLoginRepository;
import com.mysite.weddingyou_backend.plannerLogin.PlannerLoginService;

import jakarta.servlet.http.HttpSession;

@RestController //데이터를 반환
public class UserLoginController {
	
	@Autowired
	UserLoginService service;
	
	@Autowired
	UserLoginRepository userLoginRepository;
	
	@Autowired
	PlannerLoginRepository plannerLoginRepository;
	
	@Autowired
	PlannerLoginService plannerLoginService;
	
	//로그인
	@PostMapping("/user/login")
	public UserLogin login(@RequestBody UserLogin user, HttpSession session) { 
		System.out.println(user);
		UserLogin loginResult = service.login(user); //loginResult에는 dto가 저장됨
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
	public int updatePassword(@RequestParam String email, @RequestParam String password) { //리액트에서 email과 password란 이름으로 전달하도록
		int res = 0;
		List<UserLogin> userlogindata = userLoginRepository.findAll();
		List<PlannerLogin> plannerlogindata = plannerLoginRepository.findAll();
		
		for(int i =0;i<userlogindata.size();i++) {
			UserLogin user = userlogindata.get(i);
			if(userlogindata.get(i).getEmail().equals(email)) {
				res = service.updatePassword(user.getEmail(), password);
			}
		}
		for(int i=0;i<plannerlogindata.size();i++) {
			PlannerLogin planner = plannerlogindata.get(i);
			if(planner.getEmail().equals(email)) {
				res = plannerLoginService.updatePassword(planner.getEmail(),password);
			}
		}
		
		return res; //수정이 됐음1, 아니면 0
	}
	
	//컨트롤러 임시비밀번호 추가 내용
    @PostMapping("/user/forgotPassword")
    public int forgotPassword(@RequestBody UserLogin user) {
    	System.out.println(user.getEmail()); //출력 확인
        int res = service.sendTemporaryPassword(user.getEmail());
        return res;
    }


}