package com.ssafy.web.board.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardDto {

	private int article_no;
	private String user_id;
	private String user_name;
	private String subject;
	private String content;
	private int hit;
	private String register_time;

}
