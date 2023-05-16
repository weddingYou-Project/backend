package com.mysite.weddingyou_backend.estimate;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EstimateRepository extends JpaRepository<Estimate, Integer> {

	//전체 게시글 개수 조회
	@Query(value = "select count(*) from estimate",nativeQuery=true)
	int getcount();
	

	//검색어를 통한 조회
	@Query(value = "SELECT * FROM estimate WHERE (e_region LIKE CONCAT('%', :search, '%') \r\n"
			+ "OR e_dress LIKE CONCAT('%', :search, '%') \r\n"
			+ "OR e_makeup LIKE CONCAT('%', :search, '%') \r\n"
			+ "OR e_honeymoon LIKE CONCAT('%', :search, '%') \r\n"
			+ "OR e_studio LIKE CONCAT('%', :search, '%') \r\n"
			+ "OR e_title LIKE CONCAT ('%', :search, '%')) "
			+ "Order By e_id desc\r\n"
			,nativeQuery=true)
	List<Estimate> getsearchlist(String search);
	
	
	
	
	  
	//모든 데이터 조회
	List<Estimate> findAllByOrderByIdDesc();
	
	
	
	
	
	
}





//	//무한 스크롤 게시글 조회
//	@Query(value = "select * from estimate order by e_id desc LIMIT :start, :end",nativeQuery=true)
//	List<Estimate> Getlist(int start, int end);