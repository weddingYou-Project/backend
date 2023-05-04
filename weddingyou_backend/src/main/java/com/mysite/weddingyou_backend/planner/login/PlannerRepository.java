package com.mysite.weddingyou_backend.planner.login;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mysite.weddingyou_backend.planner.login.Planner;

import jakarta.transaction.Transactional;


@Repository
public interface PlannerRepository extends JpaRepository<Planner, Long> {
	//이메일로 회원 정보 조회(select * from Planner where email=?)
	Planner findByEmail(String Email); //optional은 null방지..?
	
	//insert, update, delete 할때 필요함
	@Modifying
	@Transactional
	@Query(value="update planner set password = :password where email = :email", nativeQuery=true)
	public int updatePassword(String email, String password);
}
