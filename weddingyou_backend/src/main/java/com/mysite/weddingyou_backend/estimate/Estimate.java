package com.mysite.weddingyou_backend.estimate;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Customer_Proposal")
public class Estimate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "c_id")
    private Long id;

    @Column(name = "c_title")
    private String title;		//게시글 제목

    @Column(name = "c_requirement")
    private String requirement;	//게시글 요구사항(요청사항)

    @Column(name = "c_budget")
    private int budget;			//예산

    @Column(name = "c_writer")
    private String writer;
    
    @Column(name= "c_weddingdate")	//결혼 예정일
    private String weddingdate;
    
    
    @Column(name = "c_img",length=2000)
    private String img;			//추가 이미지

    @Column(name = "c_date")
    private LocalDate date;		//날짜

    @Column(name = "c_region")
    private String region;		//지역

    @Column(name = "c_dress")
    private String dress;		//드레스

    @Column(name = "c_makeup")
    private String makeup;		//화장

    @Column(name = "c_honeymoon")
    private String honeymoon;	//신혼여행지
	
    @Column (name = "c_studio")
    private String studio;
    
    @Column (name = "c_matchstatus")
    private boolean matchstatus;     
    
}
	
	





    
	
	
	
	

