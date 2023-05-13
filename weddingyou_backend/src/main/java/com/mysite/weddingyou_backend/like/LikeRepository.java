package com.mysite.weddingyou_backend.like;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mysite.weddingyou_backend.item.Item;
import com.mysite.weddingyou_backend.item.Item.Category1;
import com.mysite.weddingyou_backend.item.Item.Category2;
import com.mysite.weddingyou_backend.plannerLogin.PlannerLogin;
import com.mysite.weddingyou_backend.userLogin.UserLogin;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Long> {	

	List<LikeEntity> findByUser(UserLogin user);
//	
	List<LikeEntity> findByUserAndItem_Category1AndItem_Category2(UserLogin user, Category1 category1, Category2 category2);
	
	List<LikeEntity> findAllByItem(Item item);
	
	List<LikeEntity> findByUserAndItem(UserLogin user, Item item);
	
	List<LikeEntity> findByPlannerAndItem(PlannerLogin planner, Item item);

}
