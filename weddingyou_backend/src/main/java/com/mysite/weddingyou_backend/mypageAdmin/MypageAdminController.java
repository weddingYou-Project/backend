package com.mysite.weddingyou_backend.mypageAdmin;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.weddingyou_backend.estimate.Estimate;
import com.mysite.weddingyou_backend.estimate.EstimateRepository;
import com.mysite.weddingyou_backend.like.LikeRepository;
import com.mysite.weddingyou_backend.payment.Payment;
import com.mysite.weddingyou_backend.payment.PaymentRepository;
import com.mysite.weddingyou_backend.plannerLogin.PlannerLogin;
import com.mysite.weddingyou_backend.plannerLogin.PlannerLoginRepository;
import com.mysite.weddingyou_backend.plannerProfile.PlannerProfileRepository;
import com.mysite.weddingyou_backend.review.Review;
import com.mysite.weddingyou_backend.review.ReviewRepository;
import com.mysite.weddingyou_backend.userLogin.UserLogin;
import com.mysite.weddingyou_backend.userLogin.UserLoginRepository;

import jakarta.transaction.Transactional;

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
	
	@Autowired
	PlannerProfileRepository plannerProfileRepository;
	
	@Autowired
	LikeRepository likeRepository;
	
	@Autowired
	EstimateRepository estimateRepository;
	
	@Autowired
	ReviewRepository reviewRepository;
	
	@Autowired
	PaymentRepository paymentRepository;
	

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
	            plannerMypageAdmin.setPlannerCareerYears(planner.getPlannerCareerYears());
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
	
	//전체 데이터 개수 조회
	@ResponseBody
	@GetMapping("/count")
	public ResponseEntity<Integer> getCount(){
		int count = mypageAdminService.getCount();
		return ResponseEntity.ok().body(count);
	}
	
	//사용자 정보 수정
	@PostMapping("/modify")
	public int updateUser(@RequestBody MypageAdmin mypageAdmin) {
	    int update = 0;

	    if ("user".equals(mypageAdmin.getType())) {
	        // mypageAdmin 테이블의 회원정보 업데이트
	        update = mypageAdminService.updateUser(mypageAdmin.getAdminId(), mypageAdmin.getUserName(),
	                mypageAdmin.getUserPassword(), mypageAdmin.getUserPhoneNum());

	        // user 테이블도 업데이트
	        userLoginRepository.updateUserByEmail(mypageAdmin.getUserEmail(), mypageAdmin.getUserName(),
	                mypageAdmin.getUserPassword(), mypageAdmin.getUserPhoneNum());
	    } else if ("planner".equals(mypageAdmin.getType())) {
	        // mypageAdmin 테이블의 플래너 정보 업데이트
	        update = mypageAdminService.updatePlanner(mypageAdmin.getAdminId(), mypageAdmin.getPlannerName(),
	                mypageAdmin.getPlannerPassword(), mypageAdmin.getPlannerPhoneNum());

	        // planner 테이블도 업데이트
	        plannerLoginRepository.updatePlannerByEmail(mypageAdmin.getPlannerEmail(), mypageAdmin.getPlannerName(),
	                mypageAdmin.getPlannerPassword(), mypageAdmin.getPlannerPhoneNum());
	    }
	    return update;
	}

	
	//사용자 정보 검색
	@GetMapping("/search")
	public ResponseEntity<Page<MypageAdmin>> getSearchList(@RequestParam String search,
			@RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size){
		
		//페이징 기능
		Pageable pageable = PageRequest.of(page, size);
		
		Page<MypageAdmin> list = mypageAdminService.getSearchList(search, pageable);

		return ResponseEntity.ok().body(list);
	}
	
	//검색 데이터 개수 조회
	@ResponseBody
	@GetMapping("/searchCount")
	public ResponseEntity<Integer> getSearchCount(@RequestParam String search){
		int count = mypageAdminService.getSearchCount(search);
		return ResponseEntity.ok().body(count);
	}
	
	//사용자 정보 삭제
	@Transactional
	@RequestMapping("/delete")
	public void delete(@RequestParam Long adminId) throws ParseException {
		// adminId에 해당하는 이메일 가져오기
	    MypageAdmin mypageAdmin = mypageAdminService.getMypageAdmin(adminId);
	    String userEmail = mypageAdmin.getUserEmail();
	    String plannerEmail = mypageAdmin.getPlannerEmail();
	    
	    //liketable 데이터 삭제
	    PlannerLogin targetPlanner = plannerLoginRepository.findByEmail(plannerEmail);
	    if(targetPlanner!=null) {
	    	 likeRepository.deleteAllByPlanner(targetPlanner);
	    }
	    UserLogin targetUser = userLoginRepository.findByEmail(userEmail);
	    if(targetUser!=null) {
	    	 likeRepository.deleteAllByUser(targetUser);
	    }
	   
	    
	    // admin 테이블에서 삭제
	 	mypageAdminService.delete(adminId);

	    // user 테이블에서 해당 이메일로 정보 삭제
	    userLoginRepository.deleteByEmail(userEmail);
	    
	    //planer 테이블에서 해당 이메일로 정보 삭제
	    plannerLoginRepository.deleteByEmail(plannerEmail);
	    
	    //plannerProfile 테이블에서 이메일로 정보 삭제
	    plannerProfileRepository.deleteByPlannerEmail(plannerEmail);
	    
	    //estimate 테이블에서 해당 planner 이메일 삭제
	    List<Estimate> estimatesData = estimateRepository.findAll();
	    for(int i = 0;i<estimatesData.size();i++) {
	    	Estimate targetEstimate = estimatesData.get(i);
	    	JSONParser parser = new JSONParser();
	    	 ArrayList<String> plannerMatching = (ArrayList<String>) parser.parse(targetEstimate.getPlannermatching()); 
	    	 ArrayList<String> userMatching = (ArrayList<String>) parser.parse(targetEstimate.getUserMatching()); 
	    	 
	    	 if(plannerEmail!=null) {
	    		 if(plannerMatching.contains(plannerEmail)) {
		    		 plannerMatching.remove(plannerEmail);	   
		    		 targetEstimate.setPlannermatching(String.valueOf(plannerMatching));
		    	 }
		    	 if(userMatching.contains(plannerEmail)) {
		    		 userMatching.remove(plannerEmail);
		    		 targetEstimate.setUserMatching(String.valueOf(userMatching));)
		    	 }
		    	 if(targetEstimate.isMatchstatus()) {
		    		 targetEstimate.setMatchstatus(false);
		    	 }
		    	 estimateRepository.save(targetEstimate);
	    	 }
	    	
	    	 
	    	 //user를 삭재헬 경우
	    	 if(userEmail!=null) {
	    		 if(targetEstimate.getWriter().equals(userEmail)) {
	    			 estimateRepository.delete(targetEstimate);
	    		 }
	    	 }

	    }
	    
	    //review 테이블에서 데이터 삭제
	    List<Review> reviewData = reviewRepository.findAll();
	    for(int i =0;i<reviewData.size();i++) {
	    	Review review = reviewData.get(i);
	     	String planneremail = review.getPlannerEmail();
	    	String useremail = review.getUserEmail();
	    	if(planneremail.equals(plannerEmail)) {
	    		reviewRepository.delete(review);
	    	}
	    	if(useremail.equals(userEmail)) {
	    		reviewRepository.delete(review);
	    	}
	    	    
	    }
	    
	    //payment 테이블에서 데이터 삭제
	    List<Payment> paymentData = paymentRepository.findAll();
	    for(int i =0;i<paymentData.size();i++) {
	    	Payment payment = paymentData.get(i);
	     	String planneremail = payment.getPlannerEmail();
	    	String useremail = payment.getUserEmail();
	    	if(planneremail.equals(plannerEmail)) {
	    		paymentRepository.delete(payment);
	    	}
	    	if(useremail.equals(userEmail)) {
	    		paymentRepository.delete(payment);
	    	}	    
	    }

	}

}