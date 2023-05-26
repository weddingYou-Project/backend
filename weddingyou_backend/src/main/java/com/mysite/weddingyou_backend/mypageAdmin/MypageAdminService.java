package com.mysite.weddingyou_backend.mypageAdmin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class MypageAdminService {
	
	@Autowired
	MypageAdminRepository mypageAdminRepository;
	
//	public List<MypageAdmin> getAllUsersAndPlanners() {
//        return mypageAdminRepository.findAll();
//    }
	
	public int updateUser(Long admin_id, String user_name, String user_password, String user_phoneNum) {
		return mypageAdminRepository.updateUser(admin_id, user_name, user_password, user_phoneNum);
	}
	
//	public int updatePlanner(String name, String password, String phoneNum, String email) {
//		return mypageAdminRepository.updatePlanner(name, password, phoneNum, email);
//	}

}
