package com.ssafy.web.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.web.member.model.MemberDto;
import com.ssafy.web.member.model.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService{

	@Autowired
	private MemberMapper memberMapper;
	
	@Override
	public MemberDto loginMember(MemberDto memberDto) {
		return memberMapper.loginMember(memberDto);
		
	}

	
	
}
