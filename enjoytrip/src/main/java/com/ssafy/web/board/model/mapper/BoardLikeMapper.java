package com.ssafy.web.board.model.mapper;

import com.ssafy.web.board.model.LikeRequestStatusDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardLikeMapper {
    int updateLikeStatus(LikeRequestStatusDto dto);
}
