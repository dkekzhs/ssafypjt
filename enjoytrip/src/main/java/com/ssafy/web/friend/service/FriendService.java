package com.ssafy.web.friend.service;


import com.ssafy.web.friend.model.FriendAddDto;
import com.ssafy.web.friend.model.FriendRequestPendingDto;

import java.util.List;
import java.util.Set;

public interface FriendService {

    int add(FriendAddDto dto) throws Exception;

    List<FriendRequestPendingDto> friendRequestPending(String id);

    int accept(FriendAddDto dto);

    Set<String> findFriends(String id);
}
