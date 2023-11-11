package com.ssafy.web.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.web.member.model.MemberDto;
import com.ssafy.web.member.service.MemberService;

@RestController
public class TestController {
	@Autowired
	private MemberService ms;
	
	@PostMapping("/user/login")
	public MemberDto login(@RequestBody MemberDto dto, HttpServletRequest httpRequest) {
		System.out.println(dto);
		HttpSession session = httpRequest.getSession(false);
		if(session  == null){
			session  =httpRequest.getSession();
		}
		MemberDto login = ms.loginMember(dto);
		System.out.println(login);
		System.out.println(session);
		return login;
	}
}
