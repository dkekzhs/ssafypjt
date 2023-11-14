package com.ssafy.web.board.controller;

import java.util.List;

import com.ssafy.web.board.model.BoardDto;
import com.ssafy.web.board.model.BoardListDto;
import com.ssafy.web.board.model.PageDto;
import com.ssafy.web.common.dto.MessageResponseDto;
import com.ssafy.web.common.dto.ResponseListDto;
import com.ssafy.web.member.model.MemberDto;

import com.ssafy.web.util.PageNavigation;

import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import com.ssafy.web.board.model.service.BoardService;

import javax.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;



@RestController
@RequestMapping("/board")
public class BoardController {

	private final BoardService boardService;

	@Autowired
	public BoardController(BoardService boardService) {
		super();
		this.boardService = boardService;
	}

	@ApiOperation(value = "회원 등록", notes = "회원을 등록합니다.")
	@PostMapping("/write")
	public ResponseEntity<MessageResponseDto> write(@RequestBody BoardDto boardDto, HttpSession session) throws Exception {
		MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
		boardDto.setUser_id(memberDto.getUser_id());

		boardService.writeArticle(boardDto);

		return ResponseEntity.ok(MessageResponseDto.builder().status(200).message("글작성성공").build());
	}

	@GetMapping("/list")
	public ResponseEntity<BoardListDto> list(@ModelAttribute PageDto page) throws Exception {
		System.out.println(page);
		List<BoardDto> list = boardService.listArticle(page);
		PageNavigation pageNavigation = boardService.makePageNavigation(page);


		return ResponseEntity.ok(BoardListDto.builder()
				.page(pageNavigation).data(list).build());
	}

	@GetMapping("/view{article_no}")
	public ResponseEntity<ResponseListDto> view(@PathVariable("article_no") int article_no)
			throws Exception {
		BoardDto boardDto = boardService.getArticle(article_no);
		boardService.updateHit(article_no);

		return ResponseEntity.ok(ResponseListDto.builder()
						.status(200).list(boardDto)
				.build());
	}


	@PostMapping("/modify")
	public ResponseEntity<MessageResponseDto> modify(BoardDto boardDto) throws Exception {
		boardService.modifyArticle(boardDto);

		return ResponseEntity.ok(MessageResponseDto.builder()
				.status(200).message("수정성공").build());
	}

	@PostMapping("/delete")
	public  ResponseEntity<MessageResponseDto> delete(@RequestBody BoardDto boardDto) throws Exception {

		boardService.deleteArticle(boardDto.getArticle_no());

		return ResponseEntity.ok(MessageResponseDto.builder()
				.status(200).message("삭제성공").build());
	}


	


}
