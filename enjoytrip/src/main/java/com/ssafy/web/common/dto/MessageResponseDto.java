package com.ssafy.web.common.dto;

import lombok.Builder;

public class MessageResponseDto {
	private int status;
	private String message;
	
	
	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	@Builder
	public MessageResponseDto(int status, String message) {
		super();
		this.status = status;
		this.message = message;
	}
}
