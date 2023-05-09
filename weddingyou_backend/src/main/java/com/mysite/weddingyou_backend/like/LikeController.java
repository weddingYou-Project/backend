package com.mysite.weddingyou_backend.like;

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

import com.mysite.weddingyou_backend.item.ItemService;
import com.mysite.weddingyou_backend.userLogin.UserLogin;
import com.mysite.weddingyou_backend.userLogin.UserLoginRepository;
import com.mysite.weddingyou_backend.userLogin.UserLoginService;

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
    public List<LikeEntity> getLikeList(HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserLogin loggedInUser = (UserLogin) session.getAttribute("loggedInUser");
        List<LikeEntity> likeList = likeService.getLikeList(Long.valueOf(loggedInUser.getUserId()));
        return likeList;
    }
	
	//좋아요 생성
	@PostMapping("/create")
	public ResponseEntity<Void> createLike(@RequestParam int itemId, HttpServletRequest request) {
	    HttpSession session = request.getSession();
	    UserLogin loggedInUser = (UserLogin) session.getAttribute("loggedInUser");

	    LikeEntity likeEntity = new LikeEntity();
	    likeEntity.setEmail(userRepository.findByEmail(loggedInUser.getEmail()));
	    likeEntity.setItemId(itemService.getItemById(itemId));

	    likeService.addLike(likeEntity);
	    return ResponseEntity.ok().build();
	}
	
	//좋아요 삭제
	@DeleteMapping("/delete/{likeId}")
	public ResponseEntity<Void> deleteLike(@PathVariable Long likeId) {
		likeService.deleteLike(likeId);
		return ResponseEntity.ok().build();
	}
	
	//필터링
	@GetMapping("/list/{category}")
	public List<LikeEntity> getLikeListByCategory(HttpServletRequest request, @RequestParam("category") String category) {
	    HttpSession session = request.getSession();
	    UserLogin loggedInUser = (UserLogin) session.getAttribute("loggedInUser");
	    List<LikeEntity> likeList = likeService.getLikeListByCategory(Long.valueOf(loggedInUser.getUserId()), category);
	    return likeList;
	}
	
	//정렬(가나다순, 인기순, 지역순)
	@GetMapping("/list/sort")
	public List<LikeEntity> getLikeListSorted(HttpServletRequest request, 
	                                         @RequestParam(value = "sortType", defaultValue = "") String sortType,
	                                         @RequestParam(value = "latitude", required = false) Double latitude,
	                                         @RequestParam(value = "longitude", required = false) Double longitude) {
	    HttpSession session = request.getSession();
	    UserLogin loggedInUser = (UserLogin) session.getAttribute("loggedInUser");
	    List<LikeEntity> likeList;
	    
	    if (latitude != null && longitude != null) {
	        // 위도, 경도 정보가 있는 경우 지역순으로 정렬
	        likeList = likeService.getLikeListSortedByLocation(Long.valueOf(loggedInUser.getUserId()), sortType, latitude, longitude);
	    } else {
	        // 위도, 경도 정보가 없는 경우 일반적인 정렬 수행
	        likeList = likeService.getLikeListSorted(Long.valueOf(loggedInUser.getUserId()), sortType);
	    }
	    return likeList;
	}

}