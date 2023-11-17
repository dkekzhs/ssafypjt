package com.ssafy.web.socket;

import java.net.URI;
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

		// 공간이 없다 => 연결을 강제로 해제시킨다.
		System.out.println("URL >> " + session.getUri());

		URI uri = session.getUri();
		if (uri == null)
			return;

		String toks[] = uri.toString().split("/");
		if (toks == null)
			return;

		switch (toks[toks.length - 1]) {
		case "createChatRoom": {
			// 방을 생성 후 현재 session client를 방에 추가
		}
			break;

		case "chat": {
			// session client를 해당 방에 추가
		}
			break;
		}
		// 채팅방 별로 sessions를 저장해야함 파라미터로 CHAT_ROOM?
		// 채팅방 팩토리가 있어야하는거 아닌가??

		var sessionId = session.getId();
		sessions.put(sessionId, session);

		// 그리고 채팅방별로 요청이 올때마다 그 sessions를 가져와서 메세지를 뿌려야한다.
		Message message = Message.builder().sender(sessionId).receiver("all").build();
		message.newConnect();

		sessions.values().forEach(s -> {
			try {
				if (!s.getId().equals(sessionId)) {
					s.sendMessage(new TextMessage(message.toString()));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		System.out.println(message.toString());
		if (session.isOpen()) {
			sessions.values().forEach(s -> {
				try {
					s.sendMessage(new TextMessage(message.toString()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			System.out.println(message.toString());
		}
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		if (session.isOpen()) {
			session.close();
		}
		super.handleTransportError(session, exception);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("연결 종료되었습니다");
		System.out.println(status);
		String sessionId = session.getId();
		sessions.remove(sessionId); // 세션 제거
	}

}
