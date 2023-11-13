package com.ssafy.web.member.model;

import lombok.Builder;

public class LoginResponseDto {
	
	private int status;
	private String message, user_name;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	@Builder
	public LoginResponseDto(int status, String message, String user_name) {
		super();
		this.status = status;
		this.message = message;
		this.user_name = user_name;
	}
	

	
}
