package com.mysite.weddingyou_backend.mypageAdmin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mysite.weddingyou_backend.plannerLogin.PlannerLogin;
import com.mysite.weddingyou_backend.userLogin.UserLogin;


@Repository
public interface MypageAdminRepository extends JpaRepository<MypageAdmin, Long> {
	
//	List<MypageAdmin> findByUserEmail(UserLogin userEmail);
//	List<MypageAdmin> findByPlannerEmail(PlannerLogin plannerEmail);
	
	boolean existsByUserEmail(String userEmail);
    boolean existsByPlannerEmail(String plannerEmail);

}
