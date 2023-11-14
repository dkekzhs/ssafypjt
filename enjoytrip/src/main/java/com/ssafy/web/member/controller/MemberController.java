package com.ssafy.web.member.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.web.common.dto.MessageResponseDto;
import com.ssafy.web.common.dto.ResponseListDto;
import com.ssafy.web.member.model.LoginResponseDto;
import com.ssafy.web.member.model.MemberDto;
import com.ssafy.web.member.service.MemberService;

@RestController
public class MemberController {
	@Autowired
	private MemberService ms;
	
	@PostMapping("/user/login")
	public ResponseEntity<LoginResponseDto> login(@RequestBody MemberDto dto, HttpServletRequest httpRequest) {
		System.out.println(dto);
		HttpSession session = httpRequest.getSession(false);
		if(session  == null){
			session  =httpRequest.getSession();
		}
		MemberDto login = ms.loginMember(dto);
		
		return ResponseEntity.ok(LoginResponseDto.builder()
				.status(200)
				.message("로그인 성공")
				.user_name(login.getUser_name())
				.build());
	}
	
	@PostMapping("/user/logout")
	public ResponseEntity<MessageResponseDto> login(HttpServletRequest request){
		HttpSession session = request.getSession(false);
		
		if(session != null) {
			session.invalidate();
		}		
		
		return ResponseEntity.ok(
				MessageResponseDto.builder().status(200).message("로그아웃 성공").build());
		
	}
}
