package com.ssafy.web.socket;

import java.util.Map;

import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.adapter.DefaultServerWebExchange;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.ssafy.web.member.model.MemberDto;

@Component
public class SocketInterceptor implements HandshakeInterceptor{

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {


		HttpServletRequest req = ((ServletServerHttpRequest)request).getServletRequest();
		HttpSession session  =  req.getSession(false);

		System.out.println("Session >> " +  session);

		if(session != null) {
			MemberDto dto = (MemberDto) session.getAttribute("user_info");
			String user_id = dto.getUser_id();
			attributes.put("user_id", user_id);
			return true;
		}
		throw new AuthException("로그인 해주세요");
		
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {
		// TODO Auto-generated method stub
		
	}

}
