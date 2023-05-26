package com.mysite.weddingyou_backend.mypageAdmin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;


@Repository
public interface MypageAdminRepository extends JpaRepository<MypageAdmin, Long> {
	
	boolean existsByUserEmail(String userEmail);
    boolean existsByPlannerEmail(String plannerEmail);
	
  	@Modifying
  	@Transactional
  	@Query(value="update MypageAdmin set user_name = :user_name, user_password = :user_password, user_phoneNum = :user_phoneNum where admin_id = :admin_id", nativeQuery=true)
  	public int updateUser(Long admin_id, String user_name, String user_password, String user_phoneNum);
  	
  	@Modifying
  	@Transactional
  	@Query(value="update MypageAdmin set planner_name = :planner_name, planner_password = :planner_password, planner_phoneNum = :planner_phoneNum where admin_id = :admin_id", nativeQuery=true)
  	public int updatePlanner(Long admin_id, String planner_name, String planner_password, String planner_phoneNum);
  	
  	//전체 데이터 개수 조회
  	@Query(value = "select count(*) from mypageAdmin", nativeQuery=true)
  	int getCount();
  	
  	//검색
  	@Query(value = "SELECT * FROM mypageAdmin WHERE (user_name LIKE CONCAT('%', :search, '%') \r\n"
            + "OR user_email LIKE CONCAT('%', :search, '%') \r\n"
            + "OR planner_name LIKE CONCAT('%', :search, '%') \r\n"
            + "OR planner_email LIKE CONCAT('%', :search, '%'))"
            + "ORDER BY admin_id ASC",
            nativeQuery = true)
    List<MypageAdmin> getSearchList(@Param("search") String search);
}
