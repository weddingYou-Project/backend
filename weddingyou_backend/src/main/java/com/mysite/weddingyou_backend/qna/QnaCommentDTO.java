package com.mysite.weddingyou_backend.qna;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QnaCommentDTO {
    private Long commentId;
    private String commentWriter;
    private String commentContent;

    public static QnaCommentDTO fromEntity(QnaComment comment) {
        QnaCommentDTO commentDTO = new QnaCommentDTO();
        commentDTO.setCommentId(comment.getCommentId());
        commentDTO.setCommentWriter(comment.getCommentWriter());
        commentDTO.setCommentContent(comment.getCommentContent());
        return commentDTO;
    }
}
