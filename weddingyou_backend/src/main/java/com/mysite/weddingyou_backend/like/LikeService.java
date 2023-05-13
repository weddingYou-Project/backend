package com.mysite.weddingyou_backend.like;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysite.weddingyou_backend.item.Item;
import com.mysite.weddingyou_backend.item.Item.Category1;
import com.mysite.weddingyou_backend.item.Item.Category2;
import com.mysite.weddingyou_backend.plannerLogin.PlannerLogin;
import com.mysite.weddingyou_backend.plannerLogin.PlannerLoginRepository;
import com.mysite.weddingyou_backend.userLogin.UserLogin;
import com.mysite.weddingyou_backend.userLogin.UserLoginRepository;


@Service
@Transactional
public class LikeService {
	
	@Autowired
	LikeRepository likeRepository;
	
	@Autowired
	UserLoginRepository userRepository;
	
	@Autowired
	PlannerLoginRepository plannerRepository;
	
	//찜목록 조회
	public List<LikeEntity> getLikeList(String email) {
	   UserLogin user = new UserLogin();
	   PlannerLogin planner = new PlannerLogin();
	   if(userRepository.findByEmail(email)!=null) {
		   user.setEmail(email);
		   return likeRepository.findByUser(user);
	   }else {
		   planner.setEmail(email);
		   return likeRepository.findByPlanner(planner);
	   }
	   
	   
        
    }

	//좋아요 추가
    public void addLike(LikeEntity likeEntity) {
    	likeRepository.save(likeEntity);
    	
        
    }
    
    //유저와 item 중복 확인
    public int checkDuplicatedUserAndItem(LikeEntity likeEntity) {
    	List<LikeEntity> likeEntities = likeRepository.findByUserAndItem(likeEntity.getUser(), likeEntity.getItem());
    	System.out.println(likeEntities);
    	if(likeEntities.size() !=0) { // 중복
    		return 1;
    	}else { //중복되지 않음
    		return 0; 
    	}
    }
    
  //플래너와 item 중복 확인
    public int checkDuplicatedPlannerAndItem(LikeEntity likeEntity) {
    	List<LikeEntity> likeEntities = likeRepository.findByPlannerAndItem(likeEntity.getPlanner(), likeEntity.getItem());
    	System.out.println(likeEntities);
    	if(likeEntities.size() !=0) { // 중복
    		return 1;
    	}else { //중복되지 않음
    		return 0; 
    	}
    }

    //좋아요 삭제
    public void deleteLike(Long likeId) {
        likeRepository.deleteById(likeId);
    }
    
    //필터링
    public List<LikeEntity> getLikeListByCategory(String email, Category1 category1, Category2 category2) {
    	UserLogin user = new UserLogin();
 	   user.setEmail(email);
 
        return likeRepository.findByUserAndItem_Category1AndItem_Category2(user, category1, category2);
    }
    
    public List<LikeEntity> getLikeListByItemId(Long itemId){

   	   Item item = new Item();
   	   item.setItemId(itemId);
   	   
    	return likeRepository.findAllByItem(item);
    }
    
    public List<LikeEntity> getLikeListByItemIdAndUser(UserLogin user, Item item) {
    	List<LikeEntity> likeItem = likeRepository.findByUserAndItem(user, item);
    	return likeItem;
    }
    
    public List<LikeEntity> getLikeListByItemIdAndPlanner(PlannerLogin planner, Item item) {
    	List<LikeEntity> likeItem = likeRepository.findByPlannerAndItem(planner, item);
    	return likeItem;
    }
    
    public List<LikeEntity> getLikeListByLikeId(Long likeId){

    	   Optional<LikeEntity> item = likeRepository.findById(likeId);
    	   LikeEntity foundItem = item.get();
    	   Item searchedItem = foundItem.getItem();

     	return likeRepository.findAllByItem(searchedItem);
     }
    
    public void increaseLikeNum(List<LikeEntity> list) {
    	for(int i=0;i<list.size();i++) {
	    	LikeEntity likeentity = list.get(i);
	    	likeentity.setLikeCount(list.size()+1);
	    	likeRepository.save(likeentity);
	    }
    }
    
    public void decreaseLikeNum(List<LikeEntity> list) {
    	System.out.println(list);
    	for(int i=0;i<list.size();i++) {
	    	LikeEntity likeentity = list.get(i);
	    	likeentity.setLikeCount(list.size()-1);
	    	likeRepository.save(likeentity);
	    }
    }
    
//    //정렬(가나다순, 인기순, 지역순)

	
}