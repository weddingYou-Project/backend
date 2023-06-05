package com.mysite.weddingyou_backend.qna;

import java.io.File;
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
    public ResponseEntity<QnaDTO> createQna(@RequestParam("file") MultipartFile file,
			@RequestParam("qnaDTO") String qnaDTOJson, @RequestHeader("category") String category) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            QnaDTO qnaDTO = objectMapper.readValue(qnaDTOJson, QnaDTO.class);

            String email = qnaDTO.getQnaWriter();

            if (category.equals("user")) {
                UserLogin user = userLoginRepository.findByEmail(email);
                if (user != null) {
                    qnaDTO.setQnaWriter(user.getName());
                }
            } else if (category.equals("planner")) {
                PlannerLogin planner = plannerLoginRepository.findByEmail(email);
                if (planner != null) {
                    qnaDTO.setQnaWriter(planner.getName());
                }
            }

            String folderPath = "C:\\Project\\customerservice";
            String originalFileName = file.getOriginalFilename();
            String filePath = folderPath + "\\" + originalFileName;
            file.transferTo(new File(filePath));
            qnaDTO.setQnaImg(originalFileName);

            qnaDTO.setQnaWriteDate(LocalDateTime.now());
            QnaDTO savedQna = qnaService.createQna(qnaDTO);
            return new ResponseEntity<>(savedQna, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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
    public List<QnaDTO> getAllQnas() {
        return qnaService.getAllQnas();
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