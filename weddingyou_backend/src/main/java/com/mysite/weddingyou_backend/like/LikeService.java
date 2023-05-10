package com.mysite.weddingyou_backend.like;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysite.weddingyou_backend.item.Item;
import com.mysite.weddingyou_backend.userLogin.UserLogin;

@Service
@Transactional
public class LikeService {
	
	@Autowired
	LikeRepository likeRepository;
	
//	//찜목록 조회
//	public List<LikeEntity> getLikeList(Long userId) {
//        return likeRepository.findByUserId(userId);
//    }

	//좋아요 추가
    public void addLike(LikeEntity likeEntity) {
        likeRepository.save(likeEntity);
    }

    //좋아요 삭제
    public void deleteLike(Long likeId) {
        likeRepository.deleteById(likeId);
    }

    //필터링
//    public List<LikeEntity> getLikeListByCategory(Long userId, String category1, String category2) {
//    	LikeEntity likeentity = new LikeEntity();
//    	UserLogin user = new UserLogin();
//    	user.setUserId(userId);
//    	likeentity.setUserId(user);
//    	Item item = likeentity.getItem();
//        return likeRepository.findByUserIdAndItemId_Category();
//    }
    
    //정렬(가나다순, 인기순, 지역순)

	
}