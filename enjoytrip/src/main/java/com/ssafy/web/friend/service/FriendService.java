package com.ssafy.web.friend.service;


import com.ssafy.web.friend.model.FriendAddDto;
import com.ssafy.web.friend.model.FriendPendingDto;

import java.util.List;
import java.util.Set;

public interface FriendService {

    int add(FriendAddDto dto) throws Exception;

    List<FriendPendingDto> friendRequestPending(String id);

    int accept(FriendAddDto dto);

    Set<String> findFriends(String id);

    int friendCount(String user_id);
}
