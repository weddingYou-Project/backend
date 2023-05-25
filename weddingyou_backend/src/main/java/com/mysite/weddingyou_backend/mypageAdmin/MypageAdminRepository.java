package com.mysite.weddingyou_backend.mypageAdmin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;


@Repository
public interface MypageAdminRepository extends JpaRepository<MypageAdmin, Long> {
	
//	List<MypageAdmin> findByUserEmail(UserLogin userEmail);
//	List<MypageAdmin> findByPlannerEmail(PlannerLogin plannerEmail);
	
	boolean existsByUserEmail(String userEmail);
    boolean existsByPlannerEmail(String plannerEmail);
	
    //mypageAdmin 부분에서 이름, 비밀번호, 휴대폰번호 수정시 사용
  	@Modifying
  	@Transactional
  	@Query(value="update user set password = :password, phone_number = :phone_number, name = :name where email = :email", nativeQuery=true)
  	public int updateUser(String email, String password, String phone_number, String name);

  	@Modifying
	@Transactional
	@Query(value="update planner set password = :password, phone_number = :phone_number, name = :name where email = :email", nativeQuery=true)
	public int updatePlanner(String email, String password, String phone_number, String name);
}
