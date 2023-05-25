package com.mysite.weddingyou_backend.notice;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class NoticeService {

	private final NoticeRepository noticeRepository;

	public NoticeService(NoticeRepository noticeRepository) {
		this.noticeRepository = noticeRepository;
	}
	// 생성
	public Notice createNotice(Notice notice) {
		return noticeRepository.save(notice);
	}
	// 
	public Notice getNotice(Long id) {
		return noticeRepository.findById(id).orElse(null);
	}
	// 수정
	public Notice updateNotice(Notice notice) {
		return noticeRepository.save(notice);
	}
	// 삭제
	public void deleteNotice(Long id) {
		noticeRepository.deleteById(id);
	}
	// 불러오기
	public List<Notice> getAllNotices() {
		return noticeRepository.findAll();
	}
}
