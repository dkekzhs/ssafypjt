package com.ssafy.web.friend.controller;

import com.ssafy.web.common.dto.ResponseDto;

import com.ssafy.web.friend.model.FriendAddDto;
import com.ssafy.web.friend.model.FriendRequestPendingDto;
import com.ssafy.web.friend.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/friend")
public class FriendController {
    private final FriendService fs;

    @Autowired
    public FriendController(FriendService fs) {
        this.fs = fs;
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseDto> add(@RequestBody FriendAddDto dto
    , HttpServletRequest request) throws Exception{
        int status = fs.add(dto);
        StringBuffer sb = new StringBuffer();
        if(status == 1){
            sb.append("친구 등록 성공");
        }
        else{
            sb.append("친구 등록 실패");
        }
        return ResponseEntity.ok(ResponseDto.builder()
                .status(200).data(sb.toString()).build());
    }

    @GetMapping("/friendRequestPending")
    public ResponseEntity<ResponseDto> friendRequestPending(@RequestBody HashMap<String,String> id,
            HttpServletRequest request) throws Exception{
        //        HttpSession session = request.getSession(false);
//        if(session != null){
//            String id =(String) session.getAttribute("id");
//            List<FriendRequestPendingDto> data = fs.friendRequestPending(id);
//            return data;
//        }
//        else{
//            throw new Exception("friend Error");
//        }
            System.out.println(id);
            List<FriendRequestPendingDto> data = fs.friendRequestPending(id.get("id"));

            return ResponseEntity.ok(ResponseDto.builder().status(200).data(data).build());
    }

    @PostMapping("/accept")
    public ResponseEntity<ResponseDto>
    friendAccept(@RequestBody FriendAddDto dto, HttpServletRequest request) throws Exception {
//        HttpSession session = request.getSession(false);
//        if(session != null){
//            String id =(String) session.getAttribute("id");
//                    return data;
//        }
//        else{
//            throw new Exception("friend Error");
//        }
        int status = fs.accept(dto);
        StringBuffer sb = new StringBuffer();
        if(status == 1){
            sb.append("친구 등록 성공");
        }
        else{
            sb.append("친구 등록 실패");
        }
        return ResponseEntity.ok(ResponseDto.builder()
                .status(200).data(sb.toString()).build());

    }




}
