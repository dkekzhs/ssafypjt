package com.ssafy.web.socket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.var;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {

	private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<String, WebSocketSession>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {

		var sessionId = session.getId();
		sessions.put(sessionId, session);
		
		Message message = Message.builder().sender(sessionId).receiver("all").build();
		message.newConnect();
		
		sessions.values().forEach(s -> {
			try {
			if(!s.getId().equals(sessionId)) {
				s.sendMessage(new TextMessage(message.toString()));
			}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		sessions.values().forEach(s -> {
			try {
				s.sendMessage(new TextMessage(message.toString()));
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		});
		System.out.println(message.toString());
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		// TODO Auto-generated method stub
		super.handleTransportError(session, exception);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("연결 종료되었습니다");
		super.afterConnectionClosed(session, status);
	}

}
