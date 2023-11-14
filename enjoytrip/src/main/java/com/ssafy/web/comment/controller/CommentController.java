package com.ssafy.web.comment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.web.comment.model.CommentDto;
import com.ssafy.web.comment.service.CommentService;
import com.ssafy.web.common.dto.MessageResponseDto;

import springfox.documentation.service.ResponseMessage;


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
	
	
	
}
