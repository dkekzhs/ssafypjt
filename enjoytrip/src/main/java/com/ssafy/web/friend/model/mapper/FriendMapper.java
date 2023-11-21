package com.ssafy.web.friend.model.mapper;


import com.ssafy.web.friend.model.FriendAddDto;
import com.ssafy.web.friend.model.FriendPendingDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface FriendMapper {
    int add(FriendAddDto friendAddDto);

    List<FriendPendingDto> friendRequestPending(String id);

    int accept(FriendAddDto dto);

    List<FriendAddDto> findFriends(String id);

    int friendCount(String user_id);

    int refuse(FriendAddDto dto);
}
