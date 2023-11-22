package com.ssafy.web.socket.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.web.comment.model.CommentDto;
import com.ssafy.web.common.dto.MessageResponseDto;
import com.ssafy.web.member.model.MemberDto;
import com.ssafy.web.socket.model.ChatRoomDto;
import com.ssafy.web.socket.service.ChatService;

@RestController
@RequestMapping("/room")
public class ChatController {

	private final ChatService chatService;

	@Autowired
	public ChatController(ChatService chatService) {
		this.chatService = chatService;
	}

	@PostMapping("/addUser")
	public ResponseEntity<MessageResponseDto> addUser(@RequestBody ChatRoomDto dto) {
		System.out.println("addUser in Controller");
		int result = chatService.addUser(dto);
		ResponseEntity<MessageResponseDto> ret = null;

		if (result == 1) {
			ret = ResponseEntity.ok(MessageResponseDto.builder().status(200).message("유저 채팅방 입장 성공").build());
		} else {
			ret = ResponseEntity.ok(MessageResponseDto.builder().status(200).message("유저 채팅방 입장 실패").build());
		}

		return ret;
	}

	@PostMapping("/isValid")
	public ResponseEntity<MessageResponseDto> isValid( HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		ChatRoomDto dto = new ChatRoomDto();
		if(session != null) {
			MemberDto memberDto = (MemberDto) session.getAttribute("user_info");
			dto.setUser_id(memberDto.getUser_id());
			String room_id = chatService.getUserRoomId(dto);
			dto.setRoom_id(room_id);
		}
		int result = chatService.isValid(dto);
		ResponseEntity<MessageResponseDto> ret = null;

		if (result == 1) {
			ret = ResponseEntity.ok(MessageResponseDto.builder().status(200).message("유저 채팅방 입장 성공").build());
		} else {
			ret = ResponseEntity.ok(MessageResponseDto.builder().status(200).message("유저 채팅방 입장 실패").build());
		}

		return ret;
	}

	@PostMapping("/deleteRoom")
	public ResponseEntity<MessageResponseDto> deleteRoom(@RequestBody ChatRoomDto dto) {
		chatService.deleteRoom(dto);
		int result = chatService.getNumOfUser(dto);
		
		ResponseEntity<MessageResponseDto> ret = null;
		if (result == 0) {
			ret = ResponseEntity.ok(MessageResponseDto.builder().status(200).message("채팅방 삭제 성공").build());
		} else {
			ret = ResponseEntity.ok(MessageResponseDto.builder().status(200).message("채팅방 삭제 실패").build());
		}

		return ret;
	}
	
	@PostMapping("/getRoomId")
	public ResponseEntity<MessageResponseDto> getRoomId(@RequestBody ChatRoomDto dto) {
		String roomId = chatService.getUserRoomId(dto);
		
		
		ResponseEntity<MessageResponseDto> ret = null;
		if (roomId == null) {
			ret = ResponseEntity.ok(MessageResponseDto.builder().status(401).message("유저에 해당하는 채팅방을 찾지 못했습니다.").build());
		} else {
			ret = ResponseEntity.ok(MessageResponseDto.builder().status(200).message("유저가 접속한 채팅방을 찾았습니다.").build());
		}

		return ret;
	}

}
