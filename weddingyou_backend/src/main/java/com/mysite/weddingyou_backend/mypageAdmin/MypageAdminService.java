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
	
	public List<MypageAdmin> getAllUsersAndPlanners() {
        return mypageAdminRepository.findAll();
    }

}
