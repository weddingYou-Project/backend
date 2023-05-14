package com.mysite.weddingyou_backend.like;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.hibernate.dialect.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.weddingyou_backend.item.Item;
import com.mysite.weddingyou_backend.item.Item.Category1;
import com.mysite.weddingyou_backend.item.Item.Category2;
import com.mysite.weddingyou_backend.item.ItemRepository;
import com.mysite.weddingyou_backend.item.ItemService;
import com.mysite.weddingyou_backend.plannerLogin.PlannerLogin;
import com.mysite.weddingyou_backend.plannerLogin.PlannerLoginRepository;
import com.mysite.weddingyou_backend.userLogin.UserLogin;
import com.mysite.weddingyou_backend.userLogin.UserLoginRepository;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/like")
public class LikeController {
	
	@Autowired
	LikeService likeService;
	
	@Autowired
	private UserLoginRepository userRepository;
	
	@Autowired
	private PlannerLoginRepository plannerRepository;
	
	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private ItemService itemService;
	
	//찜목록 조회
	@RequestMapping("/list")
    public List<String> getLikeList(@RequestBody likeDTO user, HttpServletRequest request) {
       // HttpSession session = request.getSession();
       // UserLogin loggedInUser = (UserLogin) session.getAttribute("loggedInUser");
		String email = user.getEmail();
		
        List<LikeEntity> likeList = likeService.getLikeList(email);
     
       
        List<String> encodingDatas = new ArrayList<>();
        
        
    if(likeList!=null) {
    	for(int i =0;i<likeList.size();i++) {
    		Item targetItem = likeList.get(i).getItem();
    		targetItem.setLikeWriteDate(likeList.get(i).getLikeWriteDate());
    		itemRepository.save(targetItem);
    		Category2 category2 = targetItem.getCategory2();
    		
	    	 String path = "C:/Project/itemImg/"+targetItem.getCategory1()+"/"+category2;
	    	 Path imagePath = Paths.get(path,targetItem.getItemImg());
	    	 System.out.println(imagePath);

	         try {
	             byte[] imageBytes = Files.readAllBytes(imagePath);
	             byte[] base64encodedData = Base64.getEncoder().encode(imageBytes);
	             
	             encodingDatas.add(new String(base64encodedData));
	             
	         } catch (IOException e) {
	             e.printStackTrace();
	            
	         }
	        encodingDatas.add(String.valueOf(targetItem.getItemId()));
	        encodingDatas.add(String.valueOf(likeList.get(i).getLikeWriteDate()));
	        System.out.println(targetItem.getItemId());
    	}
    	
    }
    return encodingDatas;
    
    }
	
	//좋아요 생성
	@PostMapping("/create")
	public ResponseEntity<Void> createLike(HttpServletRequest request, @RequestBody likeDTO user ) {
		//, @RequestBody likeDTO user (추가해주기)
		//@RequestParam String email, @RequestParam Long itemId ,(postman 테스트용)
//    HttpSession session = request.getSession();
//	    UserLogin loggedInUser = (UserLogin) session.getAttribute("loggedInUser");
		
		Long itemId = user.getItemId();
	 String email = user.getEmail();
	    LikeEntity likeEntity = new LikeEntity();
	//    likeEntity.setUser(userRepository.findByEmail(loggedInUser.getEmail()));
	    likeEntity.setItem(itemService.getItemById(itemId));
	    if(userRepository.findByEmail(email)!=null) {
	    	 likeEntity.setUser(userRepository.findByEmail(email));
	    	 if(likeService.checkDuplicatedUserAndItem(likeEntity)==0) {
		    	 List<LikeEntity> list = likeService.getLikeListByItemId(itemId);
		 	    likeEntity.setLikeCount(list.size()+1);
		 	    
		 	    likeService.increaseLikeNum(list);
		 	    likeService.addLike(likeEntity, itemService.getItemById(itemId));
		    }
	    }else if(plannerRepository.findByEmail(email)!=null) {
	    	likeEntity.setPlanner(plannerRepository.findByEmail(email));
	    	if(likeService.checkDuplicatedPlannerAndItem(likeEntity)==0) {
		    	 List<LikeEntity> list = likeService.getLikeListByItemId(itemId);
		 	    likeEntity.setLikeCount(list.size()+1);
		 	    
		 	    likeService.increaseLikeNum(list);
		 	    likeService.addLike(likeEntity, itemService.getItemById(itemId));
		    }
	    }

	    return ResponseEntity.ok().build();
	}
	
	//좋아요 삭제
	@PostMapping("/delete")
	public ResponseEntity<Void> deleteLike( @RequestBody likeDTO data) {
		//, @RequestBody likeDTO data (추가해주기)
		//@RequestParam String email, @RequestParam Long itemId(postman 테스트용)
		Long itemId = data.getItemId();
		String email = data.getEmail();
		
		Item item = itemService.getItemById(itemId);
		
		if(userRepository.findByEmail(email)!=null) {
			UserLogin user = userRepository.findByEmail(email);
			List<LikeEntity> likeItem = likeService.getLikeListByItemIdAndUser(user, item);
			likeService.decreaseLikeNum(likeItem);
			likeService.deleteLike(likeItem.get(0).getLikeId());
		}else if(plannerRepository.findByEmail(email)!=null) {
			PlannerLogin planner = plannerRepository.findByEmail(email);
			List<LikeEntity> likeItem = likeService.getLikeListByItemIdAndPlanner(planner, item);
			//List<LikeEntity> likeItem = likeService.getLikeList(email);
			System.out.println("newlikeItem:"+likeItem);
			likeService.decreaseLikeNum(likeItem);
			likeService.deleteLike(likeItem.get(0).getLikeId());
		}
	
		return ResponseEntity.ok().build();
	}
	
	
	@RequestMapping("/findlist")
	public int findLikeListByUserAndItem(@RequestBody likeDTO data){
		Long itemId = data.getItemId();
		String email = data.getEmail();
		
		Item item = itemService.getItemById(itemId);
		
		int res = -1;
		
		List<LikeEntity> likeItem = new ArrayList<>();
		if(userRepository.findByEmail(email)!=null) {
			UserLogin user = userRepository.findByEmail(email);
			likeItem = likeService.getLikeListByItemIdAndUser(user, item);
		
			if(likeItem.size()!=0) { //찾은 결과가 있을 때
				res = 1;
				System.out.println("=========================="+likeItem.get(0).getItem().getItemId());
			}else {
				res = 0;
			}
		}else if(plannerRepository.findByEmail(email)!=null) {
			PlannerLogin planner = plannerRepository.findByEmail(email);
			likeItem = likeService.getLikeListByItemIdAndPlanner(planner, item);
			
			if(likeItem.size()!=0) {
				res = 1;
				System.out.println("================================="+likeItem.get(0).getItem().getItemId());
			}else  {
				res = 0;
			}
		
		}
	
		return res;
	}
	//필터링
	@PostMapping("/list/category")
	public List<String> getLikeListByCategory(HttpServletRequest request, @RequestBody likeDTO data) {
	   // HttpSession session = request.getSession();
	   // UserLogin loggedInUser = (UserLogin) session.getAttribute("loggedInUser");
	   // List<LikeEntity> likeList = likeService.getLikeListByCategory(loggedInUser.getEmail(), category1, category2);
		String email =data.getEmail();
		Category1 category1 = data.getCategory1();
		
		List<LikeEntity> likeList = likeService.getLikeListByCategory1(email, category1);
	    
		 List<String> encodingDatas = new ArrayList<>();
	        
		    if(likeList!=null) {
		    	for(int i =0;i<likeList.size();i++) {
		    		Item targetItem = likeList.get(i).getItem();
		    		targetItem.setLikeWriteDate(likeList.get(i).getLikeWriteDate());
		    		itemRepository.save(targetItem);
		    		
		    		
			    	 String path = "C:/Project/itemImg/"+targetItem.getCategory1()+"/"+targetItem.getCategory2();
			    	 Path imagePath = Paths.get(path,targetItem.getItemImg());
			    	 System.out.println(imagePath);

			         try {
			             byte[] imageBytes = Files.readAllBytes(imagePath);
			             byte[] base64encodedData = Base64.getEncoder().encode(imageBytes);
			             
			             encodingDatas.add(new String(base64encodedData));
			             
			         } catch (IOException e) {
			             e.printStackTrace();
			            
			         }
			        encodingDatas.add(String.valueOf(targetItem.getItemId()));
			        encodingDatas.add(String.valueOf(likeList.get(i).getLikeWriteDate()));
			        System.out.println(targetItem.getItemId());
		    	}
		    	
		    }
		    return encodingDatas;
	
	}
	
	
	//정렬(가나다순, 인기순, 지역순)
	@GetMapping("/list/sort")
	public List<LikeEntity> getLikeList(@RequestParam("email") String email, HttpServletRequest request, @RequestParam(required = false) String sortBy) {
	  //  HttpSession session = request.getSession();
	  //  UserLogin loggedInUser = (UserLogin) session.getAttribute("loggedInUser");
	  //  List<LikeEntity> likeList = likeService.getLikeList(loggedInUser.getEmail());
		List<LikeEntity> likeList = likeService.getLikeList(email);
	    if (sortBy != null) {
	        switch (sortBy) {
	            case "name": //오름차순
	            	Collections.sort(likeList, (a, b) -> a.getItem().getItemName().compareTo(b.getItem().getItemName()));
	                break;
	            case "popularity": //내림차순
            	Collections.sort(likeList, (a, b) -> b.getLikeCount().compareTo(a.getLikeCount()));
                break;
            case "location": //오름차순
	                Collections.sort(likeList, (a, b) -> a.getLocation().compareTo(b.getLocation()));
                break;
	            default:
	                // 예외 처리
                throw new IllegalArgumentException("Invalid sort option: " + sortBy);
	        }
	    }
	    return likeList;
	}
}