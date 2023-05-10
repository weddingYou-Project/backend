package com.mysite.weddingyou_backend.like;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mysite.weddingyou_backend.item.Item;
import com.mysite.weddingyou_backend.userLogin.UserLogin;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
	
	List<LikeEntity> findByUser(String email);
//	
	List<LikeEntity> findByUserAndItem_Category1AndItem_Category2(String email, String category1, String category2);
	
	List<LikeEntity> findAllByItem(Item item);
	

}
