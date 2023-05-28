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
import com.mysite.weddingyou_backend.item.ItemDTO;
import com.mysite.weddingyou_backend.plannerUpdateDelete.PlannerUpdateDelete;
import com.mysite.weddingyou_backend.plannerUpdateDelete.PlannerUpdateDeleteRepository;
import com.mysite.weddingyou_backend.review.Review;
import com.mysite.weddingyou_backend.review.ReviewRepository;

@RestController
public class PlannerProfileController {
    private final PlannerProfileService plannerService;
    
    @Autowired
    private PlannerUpdateDeleteRepository plannerUpdateDeleteRepository;
    
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
    	  
    	  List<Review> reviews = reviewRepository.findAllByPlannerEmail(plannerEmail);
    	  System.out.println(reviews.get(0).getPlannerEmail());
    	  ArrayList<String> reviewUsers = new ArrayList<>();
    	  ArrayList<Integer> reviewStars = new ArrayList<>();
    	  int totalReviewStars = 0;
    	  int reviewCount = 0;
    	  if(reviews!=null) {
    		  for(int j = 0;j<reviews.size();j++) {
        		  reviewUsers.add(reviews.get(i).getUserEmail());
        		  reviewStars.add(reviews.get(i).getReviewStars());
        		  totalReviewStars += reviews.get(i).getReviewStars();
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
    		  Estimate targetEstimate = estimates.get(i);
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
   
    	}
    	
       
    		
	    	 
	        return encodingDatas;
    
    }
}
