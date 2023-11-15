package com.ssafy.web.member.model.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.ssafy.web.member.model.MemberDto;
import com.ssafy.web.member.model.User;

@Repository
@Mapper
public interface MemberMapper {
	public MemberDto loginMember(MemberDto memberDto);
	public String login(User user );	
	public void saveToken(User user);
}
