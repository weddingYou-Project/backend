package com.mysite.weddingyou_backend.qna;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysite.weddingyou_backend.comment.CommentDTO;
import com.mysite.weddingyou_backend.notice.NoticeDTO;
import com.mysite.weddingyou_backend.plannerLogin.PlannerLogin;
import com.mysite.weddingyou_backend.plannerLogin.PlannerLoginRepository;
import com.mysite.weddingyou_backend.userLogin.UserLogin;
import com.mysite.weddingyou_backend.userLogin.UserLoginRepository;

@RestController
@RequestMapping("/qna")
public class QnaController {
	
    private final QnaService qnaService;
	private final UserLoginRepository userLoginRepository;
	private final PlannerLoginRepository plannerLoginRepository;

    @Autowired
    public QnaController(QnaService qnaService, UserLoginRepository userLoginRepository,
			PlannerLoginRepository plannerLoginRepository) {
        this.qnaService = qnaService;
        this.userLoginRepository = userLoginRepository;
		this.plannerLoginRepository = plannerLoginRepository;
    }
    @PostMapping("/post")
	public ResponseEntity<QnaDTO> createQna(@RequestParam(required=false) MultipartFile file,
			@RequestParam("title") String title,@RequestParam("content") String content, @RequestParam("email") String email) {
		try {
			QnaDTO qnaDTO = new QnaDTO();
			qnaDTO.setQnaTitle(title);
			qnaDTO.setQnaContent(content);
			qnaDTO.setQnaViewCount(0);
			qnaDTO.setQnaWriter(email);
			String folderPath = "C:\\Project\\qnaService";
			

			File folder = new File(folderPath);
			if (!folder.exists()) {
				folder.mkdirs(); // 폴더가 존재하지 않으면 폴더 생성
			}

			if (file!=null) {
			
				Files.copy(file.getInputStream(), Paths.get(folderPath, file.getOriginalFilename()),StandardCopyOption.REPLACE_EXISTING); //request에서 들어온 파일을 uploads 라는 경로에 originalfilename을 String 으로 올림
				//file.transferTo(newFile);
				qnaDTO.setQnaImg(file.getOriginalFilename()); 
			}else {
				qnaDTO.setQnaImg(null);
			}

			QnaDTO qnadto = qnaService.createQna(qnaDTO);
			return ResponseEntity.ok(qnadto);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
   

    @PutMapping("/{qnaId}")
    public QnaDTO updateQna(@PathVariable Long qnaId, @RequestBody QnaDTO qnaDTO) {
        return qnaService.updateQna(qnaId, qnaDTO);
    }

    @DeleteMapping("/{qnaId}")
    public void deleteQna(@PathVariable Long qnaId) {
        qnaService.deleteQna(qnaId);
    }

    @GetMapping("/list")
    public List<Qna> getAllQnas() {
        return qnaService.getAllQnas2();
    }

    @GetMapping("/search/{keyword}")
    public List<QnaDTO> searchQnas(@PathVariable String keyword) {
        return qnaService.searchQnas(keyword);
    }

    @PostMapping("/{qnaId}/comments")
    public ResponseEntity<CommentDTO> createComment(@PathVariable Long qnaId,
            @RequestBody CommentDTO commentDTO) {
        CommentDTO newComment = qnaService.createComment(qnaId, commentDTO);
        return ResponseEntity.ok(newComment);
    }

    @PutMapping("/{qnaId}/comments/{commentId}")
    public CommentDTO updateComment(@PathVariable Long commentId, @RequestBody CommentDTO commentDTO) {
        return qnaService.updateComment(commentId, commentDTO);
    }

    @DeleteMapping("/{qnaId}/comments/{commentId}")
    public void deleteComment(@PathVariable Long commentId) {
        qnaService.deleteComment(commentId);
    }
}