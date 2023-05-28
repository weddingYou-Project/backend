package com.mysite.weddingyou_backend.plannerProfile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.weddingyou_backend.estimate.Estimate;
import com.mysite.weddingyou_backend.estimate.EstimateRepository;
import com.mysite.weddingyou_backend.plannerUpdateDelete.PlannerUpdateDelete;
import com.mysite.weddingyou_backend.plannerUpdateDelete.PlannerUpdateDeleteRepository;
import com.mysite.weddingyou_backend.review.Review;
import com.mysite.weddingyou_backend.review.ReviewRepository;
import com.mysite.weddingyou_backend.userUpdateDelete.UserUpdateDelete;
import com.mysite.weddingyou_backend.userUpdateDelete.UserUpdateDeleteRepository;

@RestController
public class PlannerProfileController {
    private final PlannerProfileService plannerService;
    
    @Autowired
    private PlannerUpdateDeleteRepository plannerUpdateDeleteRepository;
    
    @Autowired
    private UserUpdateDeleteRepository userUpdateDeleteRepository;
    
    @Autowired
    private ReviewRepository reviewRepository;
    
    @Autowired
    private EstimateRepository estimateRepository;

    @Autowired
    public PlannerProfileController(PlannerProfileService plannerService) {
        this.plannerService = plannerService;
    }

    @PostMapping("/plannerProfile/getProfile")
    public List<PlannerProfile> getPlannerProfile() {
       return plannerService.getPlannerProfiles();
    }
    
    @PostMapping("/plannerProfile/getProfiles1")
    public List<PlannerProfile> saveAndGetPlannerProfiles() throws ParseException {
      List<PlannerUpdateDelete> plannersInfo =  plannerUpdateDeleteRepository.findAll();
     
      for(int i =0;i<plannersInfo.size();i++) {
    	  PlannerProfile newProfile = new PlannerProfile();
    	  String plannerEmail = plannersInfo.get(i).getEmail();
    	  String plannerName  = plannersInfo.get(i).getName();
    	  String plannerIntroduction = plannersInfo.get(i).getIntroduction();
    	  String plannerPhoneNum = plannersInfo.get(i).getPhoneNum();
    	  String plannerImg = plannersInfo.get(i).getPlannerImg();
    	  LocalDateTime plannerJoinDate = plannersInfo.get(i).getPlannerJoinDate();
    	  String plannerCareerYears = plannersInfo.get(i).getPlannerCareerYears();
    	  
    	  List<Review> reviews = new ArrayList<>();
    	  System.out.println(plannerEmail);
    	  reviews = reviewRepository.findAllByPlannerEmail(plannerEmail);

    	  ArrayList<String> reviewUsers = new ArrayList<>();
    	  ArrayList<Integer> reviewStars = new ArrayList<>();
    	  int totalReviewStars = 0;
    	  int reviewCount = 0;
    	  if(reviews!=null) {
    		  for(int j = 0;j<reviews.size();j++) {
    			  System.out.println(reviews.get(j).getPlannerEmail());
        		  reviewUsers.add(reviews.get(j).getUserEmail());
        		  reviewStars.add(reviews.get(j).getReviewStars());
        		  totalReviewStars += reviews.get(j).getReviewStars();
        	  }
    		  reviewCount = reviews.size();
    	  }  
    	  
    	  double avgReviewStars = 0.0;
    	  if(reviewCount !=0) {
    		  avgReviewStars = totalReviewStars / (double)reviewCount;
    	  }
    	  
    	  String avgReviewStarsStr = String.valueOf(avgReviewStars);
    	  
    	  List<Estimate> estimates = estimateRepository.findAll();
    	  int matchingCount = 0;
    	  for(int k = 0;k<estimates.size();k++) {
    		  Estimate targetEstimate = estimates.get(k);
    		  if(targetEstimate.isMatchstatus()) {
    			  JSONParser parser = new JSONParser();
    			  ArrayList<String> matchedPlanners = (ArrayList<String>) parser.parse(targetEstimate.getPlannermatching());
    			  if(matchedPlanners.contains(plannerEmail)) {
    				  matchingCount += 1;
    			  }
    			 
    		  }
    	  }
    	  
    	  if(plannerService.getPlannerByEmail(plannerEmail)==null) {
    		  newProfile.setPlannerEmail(plannerEmail);
        	  newProfile.setPlannerName(plannerName);
        	  newProfile.setIntroduction(plannerIntroduction);
        	  newProfile.setPlannerPhoneNum(plannerPhoneNum);
        	  newProfile.setPlannerProfileImg(plannerImg);
        	  newProfile.setPlannerJoinDate(plannerJoinDate);
        	  newProfile.setPlannerCareerYears(plannerCareerYears);
        	  newProfile.setReviewCount(reviewCount);
        	  newProfile.setReviewStars(String.valueOf(reviewStars));
        	  newProfile.setReviewUsers(String.valueOf(reviewUsers));
        	  newProfile.setMatchingCount(matchingCount);
        	  newProfile.setAvgReviewStars(avgReviewStarsStr);
        	  
        	  plannerService.save(newProfile);
    	  }else {
    		  PlannerProfile targetProfile = plannerService.getPlannerByEmail(plannerEmail);
    		  
    		  targetProfile.setPlannerEmail(plannerEmail);
    		  targetProfile.setPlannerName(plannerName);
    		  targetProfile.setIntroduction(plannerIntroduction);
    		  targetProfile.setPlannerPhoneNum(plannerPhoneNum);
    		  targetProfile.setPlannerProfileImg(plannerImg);
    		  targetProfile.setPlannerJoinDate(plannerJoinDate);
    		  targetProfile.setPlannerCareerYears(plannerCareerYears);
    		  targetProfile.setReviewCount(reviewCount);
        	  targetProfile.setReviewStars(String.valueOf(reviewStars));
        	  targetProfile.setReviewUsers(String.valueOf(reviewUsers));
        	  targetProfile.setMatchingCount(matchingCount);
        	  targetProfile.setAvgReviewStars(avgReviewStarsStr);
        	  
        	  plannerService.save(targetProfile);
    	  }
    	  
      }
      
      List<PlannerProfile> foundPlannersInfo = plannerService.getPlannerProfiles();
      return foundPlannersInfo;
    }
    
    @PostMapping("/plannerProfile/getProfiles2")
    public List<String> getPlannerProfiles2() throws ParseException {
    	List<PlannerUpdateDelete> plannersInfo =  plannerUpdateDeleteRepository.findAll();
    	 List<String> encodingDatas = new ArrayList<>();
    	 
    	for(int i =0;i<plannersInfo.size();i++) {
    		PlannerUpdateDelete targetPlanner = plannerUpdateDeleteRepository.findByEmail(plannersInfo.get(i).getEmail());
    		
    		if(targetPlanner.getPlannerImg()!=null) {
    			String path = "C:/Project/profileImg/planner";
   	    	 Path imagePath = Paths.get(path,targetPlanner.getPlannerImg());
   	    	 System.out.println(imagePath);

   	         try {
   	             byte[] imageBytes = Files.readAllBytes(imagePath);
   	             byte[] base64encodedData = Base64.getEncoder().encode(imageBytes);
   	             
   	             encodingDatas.add(new String(base64encodedData));
   	             
   	         } catch (IOException e) {
   	             e.printStackTrace();
   	            
   	         }
    		}else {
    			 encodingDatas.add("null");
    		}
    		
	        encodingDatas.add(targetPlanner.getName());
	        encodingDatas.add(targetPlanner.getEmail());
    	}
    	
       
    		
	    	 
	        return encodingDatas;
    
    }
    
    @PostMapping("/plannerProfile/getProfiles3")
    public List<String> getPlannerProfiles3(@RequestParam("plannerEmailArr") String plannerEmailSort) throws ParseException {
    	System.out.println(plannerEmailSort);
    	JSONParser parser = new JSONParser();
		ArrayList<String> obj = (ArrayList<String>) parser.parse(plannerEmailSort);
		System.out.println(obj);
		 List<String> encodingDatas = new ArrayList<>();
		for(int i =0;i<obj.size();i++) {
			PlannerUpdateDelete targetPlanner =  plannerUpdateDeleteRepository.findByEmail(obj.get(i));
	    	 	 
	    		if(targetPlanner.getPlannerImg()!=null) {
	    			String path = "C:/Project/profileImg/planner";
	   	    	 Path imagePath = Paths.get(path,targetPlanner.getPlannerImg());
	   	    	 System.out.println(imagePath);

	   	         try {
	   	             byte[] imageBytes = Files.readAllBytes(imagePath);
	   	             byte[] base64encodedData = Base64.getEncoder().encode(imageBytes);
	   	             
	   	             encodingDatas.add(new String(base64encodedData));
	   	             
	   	         } catch (IOException e) {
	   	             e.printStackTrace();
	   	            
	   	         }
	    		}else {
	    			 encodingDatas.add("null");
	    		}
	    		
		        encodingDatas.add(targetPlanner.getName());
		        encodingDatas.add(targetPlanner.getEmail());
	    	
		    	 
		      
		}
		  return encodingDatas;
    
    }
    
    @PostMapping("/plannerProfile/getProfileDetail")
    public List<String> getProfileDetail(@RequestParam("plannerEmail") String plannerEmail) throws ParseException {
    	PlannerProfile targetPlannerProfile = plannerService.getPlannerByEmail(plannerEmail);
    	 List<String> result = new ArrayList<>();

    	result.add(String.valueOf(targetPlannerProfile.getReviewCount()));
    	result.add(String.valueOf(targetPlannerProfile.getAvgReviewStars()));	
    	result.add(String.valueOf(targetPlannerProfile.getIntroduction()));
    	result.add(String.valueOf(targetPlannerProfile.getMatchingCount()));
    	
    	if(!targetPlannerProfile.getReviewUsers().equals("[]")) {
    		String data = targetPlannerProfile.getReviewUsers().substring(1, targetPlannerProfile.getReviewUsers().length()-1);
        	String[] reviewUsers = data.split(",");
        
    		ArrayList<String> userName = new ArrayList<>();
        	for(int i=0;i<reviewUsers.length;i++) {
        		System.out.println(reviewUsers[i].trim());
        		UserUpdateDelete userInfo = userUpdateDeleteRepository.findByEmail(reviewUsers[i].trim());
        		System.out.println(userInfo.getName());
        		userName.add(userInfo.getName());
        	}
        	result.add(String.valueOf(userName));
    	}else {
    		result.add("[]");
    	}
    	
//    	result.add(String.valueOf(targetPlannerProfile.getReviewUsers()));
    	result.add(String.valueOf(targetPlannerProfile.getReviewStars()));
    	result.add(String.valueOf(targetPlannerProfile.getPlannerCareerYears()));
	    return result;
    
    }
    
    @PostMapping("/plannerProfile/getProfileDetail2")
    public List<String> getProfileDetail2(@RequestParam("userEmail") String userEmail,@RequestParam("estimateNum") String estimateNum ) throws ParseException {
    	List<Estimate> estimatesData = estimateRepository.findAllByWriter(userEmail);
    	String searchedPlanner = "";
    	List<String> encodingDatas = new ArrayList<>();
    	for(int i =0;i<estimatesData.size();i++) {
    		if(i==Integer.parseInt(estimateNum)) {
    			JSONParser parser = new JSONParser();
    			ArrayList<String> plannerMatching = (ArrayList<String>) parser.parse(estimatesData.get(i).getPlannermatching());
    			searchedPlanner = plannerMatching.get(0);
    			
    			break;
    		}
    	}
    	PlannerUpdateDelete targetPlanner = plannerUpdateDeleteRepository.findByEmail(searchedPlanner);
    	if(targetPlanner.getPlannerImg()!=null) {
			String path = "C:/Project/profileImg/planner";
	    	 Path imagePath = Paths.get(path,targetPlanner.getPlannerImg());
	    	 System.out.println(imagePath);

	         try {
	             byte[] imageBytes = Files.readAllBytes(imagePath);
	             byte[] base64encodedData = Base64.getEncoder().encode(imageBytes);
	             
	             encodingDatas.add(new String(base64encodedData));
	             
	         } catch (IOException e) {
	             e.printStackTrace();
	            
	         }
		}else {
			 encodingDatas.add("null");
		}
		
        encodingDatas.add(targetPlanner.getName());
        encodingDatas.add(targetPlanner.getEmail());
        return encodingDatas;
       
    }

    
}
