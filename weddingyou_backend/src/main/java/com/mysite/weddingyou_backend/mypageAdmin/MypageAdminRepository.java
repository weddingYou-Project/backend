package com.mysite.weddingyou_backend.mypageAdmin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
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
}
