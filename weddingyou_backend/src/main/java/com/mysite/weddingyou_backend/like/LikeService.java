package com.mysite.weddingyou_backend.like;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class LikeService {
	
	@Autowired
	LikeRepository likeRepository;
	
	//찜목록 조회
	public List<LikeEntity> getLikeList(String email) {
        return likeRepository.findByEmail(email);
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
    public List<LikeEntity> getLikeListByCategory(String email, String category) {
        return likeRepository.findByEmailAndItemId_Category(email, category);
    }
    
    //정렬(가나다순, 인기순, 지역순)

	
}