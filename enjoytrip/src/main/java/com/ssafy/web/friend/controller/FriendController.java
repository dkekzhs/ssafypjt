package com.ssafy.web.friend.controller;

import com.ssafy.web.common.dto.MessageResponseDto;
import com.ssafy.web.common.dto.ResponseListDto;

import com.ssafy.web.common.exception.AuthException;
import com.ssafy.web.friend.model.FriendAddDto;
import com.ssafy.web.friend.model.FriendPendingDto;
import com.ssafy.web.friend.service.FriendService;
import com.ssafy.web.member.model.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/friend")
public class FriendController {
    private final FriendService fs;

    @Autowired
    public FriendController(FriendService fs) {
        this.fs = fs;
    }

    @GetMapping("/count")
    ResponseEntity<?> FriendsCount(HttpServletRequest servletRequest) {
        HttpSession session = servletRequest.getSession(false);
        MemberDto dto = (MemberDto) session.getAttribute("user_info");
        int count = fs.friendCount(dto.getUser_id());
        return ResponseEntity.ok(MessageResponseDto.builder().message(String.valueOf(count)).build());
    }

    @PostMapping("/add")
    public ResponseEntity<MessageResponseDto> add(@RequestBody FriendAddDto dto
            , HttpServletRequest request) throws Exception {
        int status = fs.add(dto);
        StringBuffer sb = new StringBuffer();
        if (status == 1) {
            sb.append("친구 등록 성공");
        } else {
            sb.append("친구 등록 실패");
        }
        return ResponseEntity.ok(MessageResponseDto.builder()
                .status(200).message(sb.toString()).build());
    }

    @GetMapping("/friendRequestPending")
    public ResponseEntity<ResponseListDto> friendRequestPending(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession(false);
        if (session != null) {
            MemberDto dto = (MemberDto) session.getAttribute("user_info");
            List<FriendPendingDto> data = fs.friendRequestPending(dto.getUser_id());
            return ResponseEntity.ok(ResponseListDto.builder().status(200).list(data).build());
        }
        throw new AuthException("friendRequestPending error");

    }

    @PostMapping("/accept")
    public ResponseEntity<MessageResponseDto>
    friendAccept(@RequestBody FriendAddDto dto, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession(false);
        if(session != null) {
            MemberDto memberDto = (MemberDto) session.getAttribute("user_info");
            dto.setTo(memberDto.getUser_id());
            System.out.println(dto);
            int status = fs.accept(dto);
            StringBuffer sb = new StringBuffer();
            if (status == 1) {
                sb.append("친구 등록 성공");
            } else {
                sb.append("친구 등록 실패");
            }
            return ResponseEntity.ok(MessageResponseDto.builder()
                    .status(200).message(sb.toString()).build());
        }
        throw new AuthException("accpet error");
    }
    
    @GetMapping("/myfriend")
    public ResponseEntity<?> myFriend(HttpServletRequest request) throws AuthException{
        HttpSession session = request.getSession(false);
        if(session != null) {
            MemberDto memberDto = (MemberDto) session.getAttribute("user_info");
            System.out.println(memberDto);
            List<FriendAddDto> data = fs.findFriends(memberDto.getUser_id());
            return ResponseEntity.ok(ResponseListDto.builder().list(data).build());
        }
    	throw new AuthException("내친구찾기 오류");
    }
    

}
