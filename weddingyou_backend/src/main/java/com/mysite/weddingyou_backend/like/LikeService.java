package com.mysite.weddingyou_backend.like;

import java.util.Collections;
import java.util.Comparator;
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
	public List<LikeEntity> getLikeList(Long userId) {
        return likeRepository.findByUserId(userId);
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
    public List<LikeEntity> getLikeListByCategory(Long userId, String category) {
        return likeRepository.findByUserIdAndItemId_Category(userId, category);
    }
    
    //정렬(가나다순, 인기순, 지역
    public List<LikeEntity> getLikeListSorted(Long userId, String sortType) {
        List<LikeEntity> likeList;
        if (sortType.equals("alphabetical")) {
            likeList = likeRepository.findByUserIdOrderByTitleAsc(userId);
        } else if (sortType.equals("popular")) {
            likeList = likeRepository.findByUserIdOrderByLikesDesc(userId);
        } else {
            // 기본값은 등록 순으로 정렬
            likeList = likeRepository.findByUserIdOrderByIdDesc(userId);
        }
        return likeList;
    }

    public List<LikeEntity> getLikeListSortedByLocation(Long userId, String sortType, Double latitude, Double longitude) {
        List<LikeEntity> likeList = likeRepository.findByUserId(userId);
        if (sortType.equals("distance")) {
            // 거리순으로 정렬
            Collections.sort(likeList, new Comparator<LikeEntity>() {
                @Override
                public int compare(LikeEntity o1, LikeEntity o2) {
                	double distance1 = distance(latitude, longitude, o1.getItemId().getLatitude(), o1.getItemId().getLongitude());
                	double distance2 = distance(latitude, longitude, o2.getItemId().getLatitude(), o2.getItemId().getLongitude());
                    return Double.compare(distance1, distance2);
                }
            });
        }
        return likeList;
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        // 두 지점의 위도, 경도 정보를 기반으로 거리 계산
        double theta = lon1 - lon2;
        double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) 
                    + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) 
                    * Math.cos(Math.toRadians(theta));
        dist = Math.acos(dist);
        dist = Math.toDegrees(dist);
        dist = dist * 60 * 1.1515 * 1.609344; // 마일 단위를 킬로미터 단위로 변환
        return (dist);
    }


	
}