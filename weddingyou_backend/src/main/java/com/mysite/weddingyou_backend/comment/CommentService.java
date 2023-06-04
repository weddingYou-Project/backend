package com.mysite.weddingyou_backend.comment;

import org.springframework.stereotype.Service;

import com.mysite.weddingyou_backend.plannerLogin.PlannerLogin;
import com.mysite.weddingyou_backend.plannerLogin.PlannerLoginRepository;
import com.mysite.weddingyou_backend.userLogin.UserLogin;
import com.mysite.weddingyou_backend.userLogin.UserLoginRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class CommentService {

	private final CommentRepository commentRepository;
	private final UserLoginRepository userLoginRepository;
	private final PlannerLoginRepository plannerLoginRepository;

	public CommentService(CommentRepository commentRepository, UserLoginRepository userLoginRepository,
			PlannerLoginRepository plannerLoginRepository) {
		this.commentRepository = commentRepository;
		this.userLoginRepository = userLoginRepository;
		this.plannerLoginRepository = plannerLoginRepository;
	}

	public CommentDTO createComment(Long reviewId, CommentDTO commentDTO, HttpSession session) {
		String category = (String) session.getAttribute("category");
		String email = (String) session.getAttribute("email");

		Comment comment = new Comment();
		comment.setCommentContent(commentDTO.getCommentContent());
		if ("user".equals(category)) {
			UserLogin user = userLoginRepository.findByEmail(email);
			if (user != null) {
				comment.setCommentWriter(user.getName());
			}
		} else if ("planner".equals(category)) {
			PlannerLogin planner = plannerLoginRepository.findByEmail(email);
			if (planner != null) {
				comment.setCommentWriter(planner.getName());
			}
		}

		commentRepository.save(comment);
		return CommentDTO.fromEntity(comment);
	}
}