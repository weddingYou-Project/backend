package com.mysite.weddingyou_backend.notice;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notice")
public class NoticeController {

	private final NoticeService noticeService;

	public NoticeController(NoticeService noticeService) {
		this.noticeService = noticeService;
	}

	@PostMapping
	public ResponseEntity<Notice> createNotice(@RequestBody Notice notice) {
		return ResponseEntity.ok(noticeService.createNotice(notice));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Notice> getNotice(@PathVariable Long id) {
		return ResponseEntity.ok(noticeService.getNotice(id));
	}

	@PutMapping
	public ResponseEntity<Notice> updateNotice(@RequestBody Notice notice) {
		return ResponseEntity.ok(noticeService.updateNotice(notice));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteNotice(@PathVariable Long id) {
		noticeService.deleteNotice(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping
	public ResponseEntity<List<Notice>> getAllNotices() {
		return ResponseEntity.ok(noticeService.getAllNotices());
	}
}
