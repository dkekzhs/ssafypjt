package com.ssafy.web.comment.model;

import java.util.Date;

public class CommentDto {
	private int comment_id, article_no, parent_comment_id,root_id;
	private String content, user_id;
	private Date created_time;
	
	
	
	
	public int getComment_id() {
		return comment_id;
	}

	public void setComment_id(int comment_id) {
		this.comment_id = comment_id;
	}

	@Override
	public String toString() {
		return "CommentDto [article_no=" + article_no + ", comment_id="  + ", parent_comment_id="
				+ parent_comment_id + ", root_id=" + root_id + ", content=" + content + ", user_id=" + user_id
				+ ", created_time=" + created_time + "]";
	}

	public int getParent_comment_id() {
		return parent_comment_id;
	}

	public void setParent_comment_id(int parent_comment_id) {
		this.parent_comment_id = parent_comment_id;
	}
	
	
	public int getRoot_id() {
		return root_id;
	}

	public void setRoot_id(int root_id) {
		this.root_id = root_id;
	}

	public Date getCreated_time() {
		return created_time;
	}

	public void setCreated_time(Date created_time) {
		this.created_time = created_time;
	}

	public int getArticle_no() {
		return article_no;
	}
	public void setArticle_no(int article_no) {
		this.article_no = article_no;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	
}
