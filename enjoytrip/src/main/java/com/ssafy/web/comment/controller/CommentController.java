package com.ssafy.web.comment.controller;

import com.ssafy.web.comment.model.CommentDto;
import com.ssafy.web.comment.service.CommentService;
import com.ssafy.web.common.dto.MessageResponseDto;
import com.ssafy.web.common.dto.ResponseListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/comment")
public class CommentController {
	private final CommentService cs;

	@Autowired
	public CommentController(CommentService cs) {
		super();
		this.cs = cs;
	}
	
	@PostMapping("/add")
	public ResponseEntity<MessageResponseDto> add(@RequestBody CommentDto dto){
		int comment = cs.add(dto);
		if(comment == 1) {
			 ResponseEntity.ok(MessageResponseDto.builder().status(200)
						.message("댓글 달기 성공").build());
		}
		else {
			 ResponseEntity.ok(MessageResponseDto.builder().status(200)
						.message("댓글 달기 실패").build());
		}
		return ResponseEntity.ok(MessageResponseDto.builder().status(200)
				.message("댓글 달기 성공").build());
	}

	@GetMapping("/getComments")
	public ResponseEntity<ResponseListDto> getComments(@ModelAttribute CommentDto dto){
		List<CommentDto> list =  cs.getComments(dto.getArticle_no());

		return ResponseEntity.ok(ResponseListDto.builder().status(200).list(list).build());
	}

	@PostMapping("/deleteComment")
	public ResponseEntity<MessageResponseDto> deleteComment(@ModelAttribute CommentDto dto){
		int flag =  cs.deleteComment(dto.getComment_id());

		return ResponseEntity.ok(MessageResponseDto.builder()
				.status(200)
				.message(flag==1 ? "해당 댓글 지우기 성공" : "해당 댓글 지우기 실패").build());
	}
	//나중에 게시글 서비스로 겨야함
	@PostMapping("/deleteCommentByArticle_no")
	public ResponseEntity<MessageResponseDto> deleteCommentsByArticleNo(@ModelAttribute CommentDto dto){
		int flag = cs.deleteCommentsByArticleNo(dto.getArticle_no());
		return ResponseEntity.ok(MessageResponseDto.builder()
				.status(200)
				.message(flag==1 ? "해당 글 댓글 전부 삭제 성공" : "해당 글 댓글 전체 지우기 실패").build());
	}
	
}
