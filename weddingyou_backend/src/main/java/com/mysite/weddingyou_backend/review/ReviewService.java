package com.mysite.weddingyou_backend.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class ReviewService {
	
	@Autowired
	ReviewRepository reviewRepository;
	
	public void save(Review review) {
		reviewRepository.save(review);
	}


}