package com.mysite.weddingyou_backend.qna;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/qna")
public class QnaController {
    private final QnaService qnaService;

    @Autowired
    public QnaController(QnaService qnaService) {
        this.qnaService = qnaService;
    }

    @PostMapping
    public ResponseEntity<QnaDTO> createQna(@RequestBody QnaDTO qnaDTO) {
        qnaDTO.setQnaWriteDate(LocalDateTime.now());
        QnaDTO savedQna = qnaService.createQna(qnaDTO);
        return new ResponseEntity<>(savedQna, HttpStatus.CREATED);
    }

    @PutMapping("/{qnaId}")
    public QnaDTO updateQna(@PathVariable Long qnaId, @RequestBody QnaDTO qnaDTO) {
        return qnaService.updateQna(qnaId, qnaDTO);
    }

    @DeleteMapping("/{qnaId}")
    public void deleteQna(@PathVariable Long qnaId) {
        qnaService.deleteQna(qnaId);
    }

    @GetMapping
    public List<QnaDTO> getAllQnas() {
        return qnaService.getAllQnas();
    }

    @GetMapping("/search/{keyword}")
    public List<QnaDTO> searchQnas(@PathVariable String keyword) {
        return qnaService.searchQnas(keyword);
    }

    @PostMapping("/{qnaId}/comments")
    public ResponseEntity<QnaCommentDTO> createComment(@PathVariable Long qnaId,
            @RequestBody QnaCommentDTO commentDTO) {
        QnaCommentDTO newComment = qnaService.createComment(qnaId, commentDTO);
        return ResponseEntity.ok(newComment);
    }

    @PutMapping("/{qnaId}/comments/{commentId}")
    public QnaCommentDTO updateComment(@PathVariable Long commentId, @RequestBody QnaCommentDTO commentDTO) {
        return qnaService.updateComment(commentId, commentDTO);
    }

    @DeleteMapping("/{qnaId}/comments/{commentId}")
    public void deleteComment(@PathVariable Long commentId) {
        qnaService.deleteComment(commentId);
    }
}
