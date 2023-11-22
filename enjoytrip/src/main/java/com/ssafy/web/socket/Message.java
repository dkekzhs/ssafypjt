package com.ssafy.web.socket;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Message<T> {

	private String type;
	private String sender;
	private String receiver;
	private T data;
	
	public void setSender(String sender) {this.sender = sender;}
	
	public void newConnect() {
		this.type = "new";
	}
	
	public void closeConnect() {
		this.type = "close";
	}

	@Override
	public String toString() {
		return "{" +
				"'type' :'" + type +"'"+
				", 'sender' :'" + sender+"'"  +
				", 'receiver' :'" + receiver+"'" +
				", 'data' :" + data+"'" +
				'}';
	}
}
