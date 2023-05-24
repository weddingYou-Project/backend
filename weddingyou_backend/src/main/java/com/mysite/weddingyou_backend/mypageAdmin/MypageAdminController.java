package com.mysite.weddingyou_backend.mypageAdmin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MypageAdminController {
	
	@Autowired
	MypageAdminService mypageAdminService;

}
