package com.ssafy.web.member.service;

import com.ssafy.web.member.model.MemberDto;
import com.ssafy.web.member.model.RsaDto;

public interface MemberService {

	MemberDto loginMember(MemberDto dto, String ip);

	RsaDto getPublicKey(String remoteAddr);

	int insert(MemberDto dto, String ip);

}
