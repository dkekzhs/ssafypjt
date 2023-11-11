package com.ssafy.web.friend.model.mapper;


import com.ssafy.web.friend.model.FriendAddDto;
import com.ssafy.web.friend.model.FriendRequestPendingDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface FriendMapper {
    int add(FriendAddDto friendAddDto);

    List<FriendRequestPendingDto> friendRequestPending(String id);

    int accept(FriendAddDto dto);

    List<FriendAddDto> findFriends(String id);
}
