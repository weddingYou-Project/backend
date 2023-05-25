package com.mysite.weddingyou_backend.notice;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeDTO {
	 private long noticeId;
	    
	    private byte[] noticeImg;
	    
	    private String noticeTitle;
	    
	    private String noticeContent;
}
