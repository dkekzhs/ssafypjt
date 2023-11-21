package com.ssafy.web.member.service;

import java.security.spec.RSAPublicKeySpec;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.web.member.model.MemberDto;
import com.ssafy.web.member.model.RsaDto;
import com.ssafy.web.member.model.mapper.MemberMapper;
import com.ssafy.web.util.secure.RSAKeyManager;
import com.ssafy.web.util.secure.RSA_2048;
import com.ssafy.web.util.secure.SHA_512;

@Service
public class MemberServiceImpl implements MemberService{
	private final int MAX_REQUEST=100;
	private final int REQUEST_SEC=60;
	
	private RSAKeyManager keyManager = RSAKeyManager.getInstnace();

	@Autowired
	public MemberServiceImpl(MemberMapper memberMapper) {
		this.memberMapper = memberMapper;
	}

	private final MemberMapper memberMapper;
	
	@Override
	public MemberDto loginMember(MemberDto dto, String ip) {
		String privatekey = RSA_2048.keyToString(RSAKeyManager.getInstnace().getPrivateKey(ip));

		String password = RSA_2048.decrypt(dto.getUser_password(),privatekey);
		System.out.println(password);
		String salt = memberMapper.getSaltFromDB(dto.getUser_id());
		password = SHA_512.SHA512(password, salt);
			dto.setUser_password(password);
		System.out.println(dto.getUser_password());
		return memberMapper.loginMember(dto);
		
	}

	@Override
	public RsaDto getPublicKey(String ip) {
		System.out.println(ip);
		if(keyManager.getPublicKey(ip) == null) {
			keyManager.createKeyPair(ip);
		}

		RSAPublicKeySpec publicSpec = keyManager.getPublicKeySpecFromKeyManager(ip);
		String modulus = publicSpec.getModulus().toString(16);
		String exponent = publicSpec.getPublicExponent().toString(16);

		
		System.out.println("MemberServiceImpl modulus >> " + modulus + ", exponent >> " + exponent);
		return RsaDto.builder().modulus(modulus)
				.exponent(exponent).build();
	}

	@Override
	@Transactional
	public int insert(MemberDto dto, String ip) {
		String privatekey = RSA_2048.keyToString(RSAKeyManager.getInstnace().getPrivateKey(ip));
		String password = RSA_2048.decrypt(dto.getUser_password(),privatekey);
		String salt = SHA_512.getSalt();
		
		password = SHA_512.SHA512(password, salt);
		dto.setUser_password(password);

		int saltStatus = memberMapper.insertSalt(dto.getUser_id(), salt);
		int memberStatus = memberMapper.memberInsert(dto);
		//sucess
		if(saltStatus ==1 && memberStatus ==1) {
			return 1;
		}
		return 0;
	}

	@Override
	public List<String> getUserList(String user_id, String search) {
		List<String> userList = memberMapper.getUserList(user_id, search);
		System.out.println(userList);
		return userList;
	}



}
