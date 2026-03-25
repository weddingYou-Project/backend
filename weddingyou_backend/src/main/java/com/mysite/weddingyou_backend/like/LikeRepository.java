package com.mysite.weddingyou_backend.like;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Integer> {
//	List<Like> findByUserId(Integer userId);
//    Optional<Like> findByUserIdAndItemId(Integer userId, Integer itemId);
}
