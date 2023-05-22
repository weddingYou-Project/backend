package com.mysite.weddingyou_backend.review;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service
@Transactional
public class ReviewService {
	
	@Autowired
	ReviewRepository reviewRepository;
	
	public Review createReview(ReviewDTO reviewDTO, MultipartFile file) throws IOException {
        // reviewDTO와 file을 활용하여 리뷰 생성 로직을 처리한다
        Review review = new Review();
        // 리뷰 객체에 필요한 정보를 설정한다
        review.setReviewStars(reviewDTO.getReviewStars());
        review.setReviewText(reviewDTO.getReviewText());
        review.setReviewImg(reviewDTO.getReviewImg());

        // 생성된 리뷰를 데이터베이스에 저장하고 ID를 할당한다
        reviewRepository.save(review);

        return review;
    }


}
