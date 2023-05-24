package com.mysite.weddingyou_backend.userLogin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mysite.weddingyou_backend.userLogin.UserLogin;

import jakarta.transaction.Transactional;


@Repository
public interface UserLoginRepository extends JpaRepository<UserLogin, Long> {
	//이메일로 회원 정보 조회(select * from User where email=?)
	UserLogin findByEmail(String Email); //optional은 null방지..?
	
	//insert, update, delete 할때 필요함
	@Modifying
	@Transactional
	@Query(value="update user set password = :password where email = :email", nativeQuery=true)
	public int updatePassword(String email, String password);
	
	//mypageAdmin 부분에서 이름, 비밀번호, 휴대폰번호 수정시 사용
	@Modifying
	@Transactional
	@Query(value="update user set password = :password, phone_number = :phone_number, name = :name where email = :email", nativeQuery=true)
	public int updateUser(String email, String password, String phone_number, String name);
}
