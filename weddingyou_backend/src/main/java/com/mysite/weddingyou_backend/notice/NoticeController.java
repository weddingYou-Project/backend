package com.mysite.weddingyou_backend.notice;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notice")
public class NoticeController {

	private NoticeService noticeService;

}
