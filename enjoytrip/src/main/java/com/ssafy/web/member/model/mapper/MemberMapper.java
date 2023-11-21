package com.ssafy.web.member.model.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.ssafy.web.member.model.MemberDto;
import com.ssafy.web.member.model.User;

import java.util.List;

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


	List<String> getUserList(@Param("user_id") String user_id, @Param("search") String search);

}
