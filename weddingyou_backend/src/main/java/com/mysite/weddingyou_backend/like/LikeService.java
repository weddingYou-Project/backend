package com.mysite.weddingyou_backend.like;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	   
        return likeRepository.findByUser(email);
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
    public List<LikeEntity> getLikeListByCategory(String email, String category1, String category2) {
    	List<LikeEntity> likelist = getLikeList(email);
    	
        return likeRepository.findByUserAndItem_Category1AndItem_Category2(email, category1, category2);
    }
    
//    //정렬(가나다순, 인기순, 지역순)

	
}