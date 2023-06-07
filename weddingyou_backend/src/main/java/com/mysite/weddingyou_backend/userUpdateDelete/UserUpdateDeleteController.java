package com.mysite.weddingyou_backend.userUpdateDelete;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mysite.weddingyou_backend.comment.Comment;
import com.mysite.weddingyou_backend.comment.CommentRepository;
import com.mysite.weddingyou_backend.estimate.Estimate;
import com.mysite.weddingyou_backend.estimate.EstimateRepository;
import com.mysite.weddingyou_backend.like.LikeRepository;
import com.mysite.weddingyou_backend.mypageAdmin.MypageAdmin;
import com.mysite.weddingyou_backend.mypageAdmin.MypageAdminRepository;
import com.mysite.weddingyou_backend.mypageAdmin.MypageAdminService;
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

@RestController //데이터를 반환
public class UserUpdateDeleteController {
	
	@Autowired
	UserUpdateDeleteService service;

	
	@Autowired
	PlannerLoginRepository plannerRepository;
	
	@Autowired
	UserLoginRepository userRepository;
	
	@Autowired
	LikeRepository likeRepository;
	
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
	EstimateRepository estimateRepository;
	
	@Autowired
	ReviewRepository reviewRepository;
	
	@Autowired
	PaymentRepository paymentRepository;
	
	@Autowired
	CommentRepository commentRepository;
	

	 @PostMapping("/user/userSearch")
	 public UserUpdateDelete searchUser(@RequestBody UserUpdateDeleteDTO user) throws Exception {
		
		 
		 	UserUpdateDelete searchedUser = service.getUserByEmail(user.getEmail());
		 	if(searchedUser != null) {
	        	 return searchedUser;
	        }else {
	        	throw new Exception("이메일이 중복되지 않습니다!");
	        }
		   
	 }

	 	@Transactional
	 	 @PostMapping("/user/userDelete")
		public void delete(@RequestParam String category, @RequestParam String email) throws ParseException {
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
	 	    
	 		Long adminId = null;
	 		 MypageAdmin mypageAdmin = null;
	 		if(category.equals("user")) {
	 			MypageAdmin targetdata = mypageAdminRepository.findByUserEmail(email);
	 			adminId = targetdata.getAdminId();
	 		
	 			
	 		}else if(category.equals("planner")) {
	 			MypageAdmin targetdata = mypageAdminRepository.findByPlannerEmail(email);
	 			adminId = targetdata.getAdminId();
	 			
	 		}
	 			
	 			
	 		// adminId에 해당하는 이메일 가져오기
	 		
	 		mypageAdmin = mypageAdminService.getMypageAdmin(adminId);
		    String userEmail = null;
		    String plannerEmail = null;
		   
		    userEmail = mypageAdmin.getUserEmail();
			plannerEmail = mypageAdmin.getPlannerEmail();
	
		   
		    
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
			    		 if(targetEstimate.isMatchstatus()) {
				    		 targetEstimate.setMatchstatus(false);
				    	 }
			    	 }
			    	 if(userMatching.contains(plannerEmail)) {
			    		 userMatching.remove(plannerEmail);
			    		 targetEstimate.setUserMatching(String.valueOf(userMatching));
			    		 if(targetEstimate.isMatchstatus()) {
				    		 targetEstimate.setMatchstatus(false);
				    	 }
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
		    
		    List<Comment> commentData = commentRepository.findAll();
		    for(int i =0;i<commentData.size();i++) {
		    	Comment comment  = commentData.get(i);
		     	String targetEmail = comment.getCommentEmail();
		    	
		    	if(targetEmail.equals(plannerEmail)) {
		    		commentRepository.delete(comment);
		    	}
		    	if(targetEmail.equals(userEmail)) {
		    		commentRepository.delete(comment);
		    	}	    
		    }

		}
//	 @PostMapping("/user/userDelete")
//	    public ResponseEntity<UserUpdateDelete> deleteUser(@RequestBody UserUpdateDeleteDTO user) {
//		 UserUpdateDelete searchedUser = service.getUserByEmail(user.getEmail());
//		service.delete(searchedUser);
//		return ResponseEntity.status(HttpStatus.OK).build();
//	    }
	 
	 @PostMapping("/user/userUpdate")
	    public UserUpdateDelete updateUser(@RequestBody UserUpdateDeleteDTO user) throws Exception {
		 UserUpdateDelete searchedUser = service.getUserByEmail(user.getEmail());
			
		 if(searchedUser!= null) {
			 searchedUser.setEmail(user.getEmail());
			 searchedUser.setPassword(user.getPassword());
			 searchedUser.setPhoneNum(user.getPhoneNum());
			 searchedUser.setName(user.getName());
			 searchedUser.setGender(user.getGender());
			
			 service.save(searchedUser);
		 }else {
			 throw new Exception("변경할 이메일이 존재하지 않습니다!");
		 }
		 return searchedUser;
		
	    }
	 
	 @PostMapping("/user/updateprofileImg")
	 public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("useremail") String email) {
		    try {	
		    	UserUpdateDelete searchedUser = service.getUserByEmail(email);
		    	String path1 = "C:\\Project";
		    	String path2 = "C:\\Project\\profileImg";
		    	String path3 = "C:\\Project\\profileImg\\user";
		    	File folder1 = new File(path1);
		    	File folder2 = new File(path2);
		    	File folder3 = new File(path3);
		    	if(!folder1.exists() || !folder2.exists() || !folder3.exists()) {
		    		try {
		    			folder1.mkdir();
		    			folder2.mkdir();
		    			folder3.mkdir();
		    		}catch(Exception e) {
		    			e.getStackTrace();
		    		}
		    	}
		    	
		    	
		    	if(searchedUser.getUserImg() != null) {
		    		Path deleteFilePath = Paths.get(path3, searchedUser.getUserImg());
		    		Files.delete(deleteFilePath);
		    	}
		    	
		        Files.copy(file.getInputStream(), Paths.get("C:/Project/profileImg/user", file.getOriginalFilename()),StandardCopyOption.REPLACE_EXISTING); //request에서 들어온 파일을 uploads 라는 경로에 originalfilename을 String 으로 올림
		        System.out.println(file.getInputStream());
		        searchedUser.setUserImg(file.getOriginalFilename()); //searchedUser에다가 이미지 파일 이름 저장
		        //searchedUser.setUserImgUrl((Blob) file);
		        service.save(searchedUser); // 이미지파일이름 데이터베이스에 업데이트함
		        System.out.println(searchedUser.getUserImg());
		        return ResponseEntity.ok().build();
		    } catch (IOException e) {
		        e.printStackTrace();
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		    }
		}
	
	 
	 @RequestMapping(value="/user/getprofileImg",  produces = MediaType.IMAGE_JPEG_VALUE)
	 public ResponseEntity<byte[]> getImage(@RequestBody UserUpdateDeleteDTO user) throws Exception {
		// System.out.println("유저이메일: " + user.getEmail());
		 UserUpdateDelete searchedUser = null;
		 try {
			 searchedUser = service.getUserByEmail(user.getEmail());
		 }catch(Exception e) {
			  throw new Exception("서버 오류!");
		 }
	     if (searchedUser != null) {
	    	 try {
	    		 	Path imagePath = Paths.get("C:/Project/profileImg/user",searchedUser.getUserImg());

	             	 byte[] imageBytes = Files.readAllBytes(imagePath);
	            
	            	 byte[] base64encodedData = Base64.getEncoder().encode(imageBytes);
		             return ResponseEntity.ok()
		                      .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + 
		                    		  searchedUser.getUserImg() + "\"")
		                      .body(base64encodedData);
	           
	            
	         } catch (Exception e) {
	             e.printStackTrace();
	             throw new Exception("프로필 사진이 없습니다!");
	         }
	 
	     } else {
	        throw new Exception("로그인 하세요!");
	     }
		 
	   
	 }
	 
	 	

}