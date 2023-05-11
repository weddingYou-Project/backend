package com.mysite.weddingyou_backend.like;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.weddingyou_backend.item.Item.Category1;
import com.mysite.weddingyou_backend.item.Item.Category2;
import com.mysite.weddingyou_backend.item.ItemService;
import com.mysite.weddingyou_backend.userLogin.UserLogin;
import com.mysite.weddingyou_backend.userLogin.UserLoginRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/like")
public class LikeController {
	
	@Autowired
	LikeService likeService;
	
	@Autowired
	private UserLoginRepository userRepository;

	@Autowired
	private ItemService itemService;
	
	//찜목록 조회
	@GetMapping("/list")
    public List<LikeEntity> getLikeList(@RequestParam String email, HttpServletRequest request) {
       // HttpSession session = request.getSession();
       // UserLogin loggedInUser = (UserLogin) session.getAttribute("loggedInUser");
		
        List<LikeEntity> likeList = likeService.getLikeList(email);
        return likeList;
    }
	
	//좋아요 생성
	@PostMapping("/create")
	public ResponseEntity<Void> createLike(@RequestParam Long itemId, @RequestParam("email") String email,HttpServletRequest request) {
//	    HttpSession session = request.getSession();
//	    UserLogin loggedInUser = (UserLogin) session.getAttribute("loggedInUser");
		
	    LikeEntity likeEntity = new LikeEntity();
	//    likeEntity.setUser(userRepository.findByEmail(loggedInUser.getEmail()));
	    likeEntity.setUser(userRepository.findByEmail(email));
	    likeEntity.setItem(itemService.getItemById(itemId));
	    if(likeService.checkDuplicatedUserAndItem(likeEntity)==0) {
	    	 List<LikeEntity> list = likeService.getLikeListByItemId(itemId);
	 	    likeEntity.setLikeCount(list.size()+1);
	 	    
	 	    likeService.increaseLikeNum(list);
	 	    likeService.addLike(likeEntity);
	    }
	   
	    return ResponseEntity.ok().build();
	}
	
	//좋아요 삭제
	@DeleteMapping("/delete/{likeId}")
	public ResponseEntity<Void> deleteLike(@PathVariable Long likeId) {
		
		List<LikeEntity> list= likeService.getLikeListByLikeId(likeId);
		likeService.decreaseLikeNum(list);
		likeService.deleteLike(likeId);
		return ResponseEntity.ok().build();
	}
	
	
	//필터링
	@GetMapping("/list/{category1}/{category2}")
	public List<LikeEntity> getLikeListByCategory(HttpServletRequest request, @RequestParam("email") String email, @PathVariable("category1") Category1 category1, @PathVariable("category2") Category2 category2) {
	   // HttpSession session = request.getSession();
	   // UserLogin loggedInUser = (UserLogin) session.getAttribute("loggedInUser");
	   // List<LikeEntity> likeList = likeService.getLikeListByCategory(loggedInUser.getEmail(), category1, category2);
		List<LikeEntity> likeList = likeService.getLikeListByCategory(email, category1, category2);
	    return likeList;
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