package com.ssafy.web.comment.model.mapper;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.ssafy.web.comment.model.CommentDto;

@Mapper
@Repository
public interface CommentMapper {

	int add(CommentDto dto) throws SQLException;

}
