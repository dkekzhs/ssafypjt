package com.ssafy.web.member.model.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.ssafy.web.member.model.MemberDto;
import com.ssafy.web.member.model.User;

@Repository
@Mapper
public interface MemberMapper {
	MemberDto loginMember(MemberDto memberDto);

	int memberInsert(MemberDto member);

	void updateProfile(MemberDto member);

	void updatePassword(MemberDto m, String newPwd);

    void delete(MemberDto m);

	boolean checkId(String id);

	int insertSalt(String id, String salt) ;

	String getSaltFromDB(String id) ;
}
