package com.mysite.weddingyou_backend.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReviewController {
	
	@Autowired
	ReviewService reviewService;

}
