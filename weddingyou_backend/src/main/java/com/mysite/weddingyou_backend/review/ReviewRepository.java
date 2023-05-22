package com.mysite.weddingyou_backend.review;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mysite.weddingyou_backend.userLogin.UserLogin;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

	Review findByUserEmail(UserLogin userEmail);
	
}
