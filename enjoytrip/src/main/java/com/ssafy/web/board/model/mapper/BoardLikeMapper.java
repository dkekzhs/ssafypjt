package com.ssafy.web.board.model.mapper;

import com.ssafy.web.board.model.BoardLikeVO;
import com.ssafy.web.board.model.LikeRequestStatusDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardLikeMapper {
    int updateLikeStatus(LikeRequestStatusDto dto);

    int executeAllQueries(BoardLikeVO dto);
    boolean isExistLike(BoardLikeVO dto);
    int insertLike(BoardLikeVO dto);

    int updateLike(BoardLikeVO dto);

    int deleteLike(BoardLikeVO dto);
}
