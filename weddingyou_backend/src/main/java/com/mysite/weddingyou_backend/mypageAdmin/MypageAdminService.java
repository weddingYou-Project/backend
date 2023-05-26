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
	
	public int updatePlanner(Long admin_id, String planner_name, String planner_password, String planner_phoneNum) {
		return mypageAdminRepository.updatePlanner(admin_id, planner_name, planner_password, planner_phoneNum);
	}
	
	//전체 데이터 개수 조회
	public int getCount() {
		int count = mypageAdminRepository.getCount();
		return count;
	}
	
	//검색
	public List<MypageAdmin> getSearchList(String search){
		List<MypageAdmin> list = mypageAdminRepository.getSearchList(search);
		return list;
	}

}
