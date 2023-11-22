package com.ssafy.web.socket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message {

	private String type;
	private String sender;
	private String receiver;
	private Object data;
	
	public void setSender(String sender) {this.sender = sender;}
	
	public void newConnect() {
		this.type = "new";
	}
	
	public void closeConnect() {
		this.type = "close";
	}

	@Override
	public String toString() {
		return "data : {" +
				"type :'" + type +
				", sender :'" + sender  +
				", receiver :'" + receiver +
				", data :" + data +
				'}';
	}
}
