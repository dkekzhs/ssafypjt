package com.ssafy.web.friend.service;

import com.ssafy.web.friend.model.FriendAddDto;
import com.ssafy.web.friend.model.FriendPendingDto;
import com.ssafy.web.friend.model.mapper.FriendMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FriendServiceImpl implements FriendService{
    private final FriendMapper fm;

    @Autowired
    public FriendServiceImpl(FriendMapper fm) {
        this.fm = fm;
    }

    @Override
    public int add(FriendAddDto dto) throws Exception {
        return fm.add(dto);
    }

    @Override
    public List<FriendPendingDto> friendRequestPending(String id) {
        return fm.friendRequestPending(id);
    }

    @Override
    public int accept(FriendAddDto dto) {
        return fm.accept(dto);
    }

    @Override
    @Transactional
    public Set<String> findFriends(String id) {
        List<FriendAddDto> friends = fm.findFriends(id);
        Set<String> friendNames = new HashSet<>();
        for (FriendAddDto f: friends) {
            if (f.getFrom().equals(id)) {
                friendNames.add(f.getTo());
            } else {
                friendNames.add(f.getFrom());
            }
        }
        return friendNames;
    }

    @Override
    public int friendCount(String user_id) {
        return  fm.friendCount(user_id);
    }
}
