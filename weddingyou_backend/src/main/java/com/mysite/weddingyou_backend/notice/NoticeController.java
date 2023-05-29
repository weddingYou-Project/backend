package com.mysite.weddingyou_backend.notice;

import java.io.File;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @PostMapping("/post")
    public ResponseEntity<NoticeDTO> createNotice(@RequestParam("file") MultipartFile file, @RequestParam("noticeDTO") String noticeDTOJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            NoticeDTO noticeDTO = objectMapper.readValue(noticeDTOJson, NoticeDTO.class);

            String folderPath = "C:\\Project\\customerservice";
            String filePath = folderPath + "\\" + file.getOriginalFilename();

            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs();  // 폴더가 존재하지 않으면 폴더 생성
            }
            
            if (!file.isEmpty()) {
                File newFile = new File(filePath);
                file.transferTo(newFile);
                noticeDTO.setAttachment(filePath);
            }

            NoticeDTO newNotice = noticeService.createNotice(noticeDTO);
            return ResponseEntity.ok(newNotice);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping("/{noticeId}")
    public NoticeDTO updateNotice(@PathVariable Long noticeId, @RequestBody NoticeDTO noticeDTO) {
        return noticeService.updateNotice(noticeId, noticeDTO);
    }

    @DeleteMapping("/delete/{noticeId}")
    public void deleteNotice(@PathVariable Long noticeId) {
        noticeService.deleteNotice(noticeId);
    }

    @GetMapping("/{noticeId}")
    public NoticeDTO getNoticeById(@PathVariable Long noticeId) {
        return noticeService.getNoticeById(noticeId);
    }

    @GetMapping("/search/{keyword}")
    public List<NoticeDTO> searchNotices(@RequestParam String keyword) {
        return noticeService.searchNotices(keyword);
    }
}
