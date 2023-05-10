package com.mysite.weddingyou_backend.like;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
	
	List<LikeEntity> findByEmail(String email);
	
	List<LikeEntity> findByEmailAndItemId_Category(String email, String category);
	

}
