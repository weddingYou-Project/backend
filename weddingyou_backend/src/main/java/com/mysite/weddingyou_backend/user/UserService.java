package com.mysite.weddingyou_backend.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import jakarta.transaction.Transactional;
import java.util.Optional;


@Service
@Transactional
public class UserService {
	 
	@Autowired
	private UserRepository userRepository;
	
	public void save(UserEntity userEntity) {
		//repository의 save 메소드 소환
		userRepository.save(userEntity);
		// repository의 save 메서드 호출(조건. entity 객체를 넘겨줘야 함)
	}
	
	public UserEntity login(UserEntity userEntity) {
		/*
		 1. 회원이 입력한 이메일로 DB에서 조회
		 2. DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단
		 */
		Optional<UserEntity> user = userRepository.findByEmail(userEntity.getEmail());
		//optional 객체가 되는거임
		
		if(user.isPresent()) {
			UserEntity userData = user.get(); //get해야지 userentity 객체가 반환되어 저장
			//조회 결과가 있음(해당 이메일을 가진 회원 정보가 있다)
			if(userData.getPassword().equals(userEntity.getPassword())) {
				//비밀번호 일치
				return userEntity;
			}else {
				//비밀번호 불일치(로그인 실패)
				return null;
			}
		}else {
			//조회 결과가 없음(해당 이메일을 가진 회원 정보가 없다)
			return null;
		}
	}
	
	public int updatePassword(String email, String password) {
		return userRepository.updatePassword(email, password);
	}

}
