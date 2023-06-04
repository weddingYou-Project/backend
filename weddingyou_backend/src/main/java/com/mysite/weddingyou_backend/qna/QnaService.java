package com.mysite.weddingyou_backend.qna;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class QnaService {
    private final QnaRepository qnaRepository;
    private final QnaCommentRepository commentRepository;

    public QnaService(QnaRepository qnaRepository, QnaCommentRepository commentRepository) {
        this.qnaRepository = qnaRepository;
        this.commentRepository = commentRepository;
    }

    public QnaDTO createQna(QnaDTO qnaDTO) {
        Qna qna = new Qna();
        qna.setQnaWriter(qnaDTO.getQnaWriter());
        qna.setQnaTitle(qnaDTO.getQnaTitle());
        qna.setQnaContent(qnaDTO.getQnaContent());
        qna.setQnaWriteDate(LocalDateTime.now());
        Qna savedQna = qnaRepository.save(qna);
        return QnaDTO.fromEntity(savedQna);
    }

    public QnaDTO updateQna(Long qnaId, QnaDTO qnaDTO) {
        Qna qna = qnaRepository.findById(qnaId).orElseThrow(() -> new IllegalArgumentException("Q&A not found."));
        qna.setQnaWriter(qnaDTO.getQnaWriter());
        qna.setQnaTitle(qnaDTO.getQnaTitle());
        qna.setQnaContent(qnaDTO.getQnaContent());
        qna.setQnaWriteDate(LocalDateTime.now());
        Qna updatedQna = qnaRepository.save(qna);
        return QnaDTO.fromEntity(updatedQna);
    }

    public void deleteQna(Long qnaId) {
        qnaRepository.deleteById(qnaId);
    }

    public QnaDTO getQnaById(Long qnaId) {
        Qna qna = qnaRepository.findById(qnaId).orElseThrow(() -> new IllegalArgumentException("Q&A not found."));
        return QnaDTO.fromEntity(qna);
    }

    public List<QnaDTO> searchQnas(String keyword) {
        List<Qna> qnas = qnaRepository.findByQnaTitleContaining(keyword);
        return qnas.stream().map(QnaDTO::fromEntity).collect(Collectors.toList());
    }

    public List<QnaDTO> getAllQnas() {
        List<Qna> qnas = qnaRepository.findAll();
        return qnas.stream().map(QnaDTO::fromEntity).collect(Collectors.toList());
    }

    public QnaCommentDTO createComment(Long qnaId, QnaCommentDTO commentDTO) {
        Qna qna = qnaRepository.findById(qnaId)
                .orElseThrow(() -> new IllegalArgumentException("Q&A not found."));

        QnaComment comment = new QnaComment();
        comment.setCommentContent(commentDTO.getCommentContent());
        comment.setQna(qna);

        qna.getComments().add(comment);
        commentRepository.save(comment);

        return QnaCommentDTO.fromEntity(comment);
    }

    public QnaCommentDTO updateComment(Long commentId, QnaCommentDTO commentDTO) {
        QnaComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found."));

        comment.setCommentContent(commentDTO.getCommentContent());

        commentRepository.save(comment);

        return QnaCommentDTO.fromEntity(comment);
    }

    public void deleteComment(Long commentId) {
        QnaComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found."));

        commentRepository.delete(comment);
    }
}
