package com.mysite.weddingyou_backend.review;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Review")
public class Review {

	//id
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private long reviewId;

	//작성자
	@Column(name = "review_writer")
    private String reviewWriter;

	//제목
	@Column(name = "review_title")
    private String reviewTitle;

	//내용
	@Column(name = "review_content")
    private String reviewContent;

	//첨부파일
	@Column(name = "review_attachedfile")
    private String reviewImg;

	//작성일
	@Column(name = "review_write_date")
    private LocalDateTime reviewWriteDate;

}
