package com.mysite.weddingyou_backend.like;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysite.weddingyou_backend.item.Item;
import com.mysite.weddingyou_backend.item.Item.Category1;
import com.mysite.weddingyou_backend.item.Item.Category2;
import com.mysite.weddingyou_backend.userLogin.UserLogin;


@Service
@Transactional
public class LikeService {
	
	@Autowired
	LikeRepository likeRepository;
	
	//찜목록 조회
	public List<LikeEntity> getLikeList(String email) {
	   UserLogin user = new UserLogin();
	   user.setEmail(email);
	   
        return likeRepository.findByUser(user);
    }

	//좋아요 추가
    public void addLike(LikeEntity likeEntity) {
        likeRepository.save(likeEntity);
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