package com.ssafy.web.socket;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.web.socket.model.ChatRoomDto;
import com.ssafy.web.socket.service.ChatService;

import lombok.var;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {

	@Autowired
	private ChatService chatService;

	// 모든 세션 객체를 저장하는 해시맵
	private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<String, WebSocketSession>();

	// 방장 session id를 room id로 사용하므로 채팅원의 session id를 키 값으로 방장 session id (room id)를
	// 받아오는 해시맵
	private final Map<String, String> sessionToRoomId = new ConcurrentHashMap<String, String>();

	/**
	 * 헤더 쿠키에 담긴 id=??? 정보로 부터 채팅 접속 유저의 아이디를 얻어온다. 웹 소켓 연결 전 http 통신을 통해 핸드쉐이크하므로
	 * 쿠키를 얻어올 수 있다.
	 * 
	 * @param session
	 * @return UUID
	 */
	public String getIdFromHeader(WebSocketSession session) {
		HttpHeaders headers = session.getHandshakeHeaders();
		List<String> cookies = headers.get(HttpHeaders.COOKIE);
		String id = null;
		for (String cookie : cookies) {
			String[] parts = cookie.trim().split("=");
			String key = parts[0];
			String value = parts[1];
			if (key.equals("id")) {
				id = value;
				break;
			}
		}

		return id;
	}

	/**
	 * 웹 소켓 연결 수립 후 요청 URI에 따라 방장이 채팅방을 생성하거나, 채팅원이 채팅방에 입장함을 알 수 있다.
	 * 
	 * (chat_room db에 채팅방을 개설한 방장은 초대한 유저들을 DB에 자신의 session UUID와 함께 초대할 유저 정보를 선행하여
	 * 저장해놔야한다.)
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		ChatRoom room = null;
		String roomId = null;
		String toks[] = null;
		URI uri = null;

		System.out.println("URL >> " + session.getUri());
		System.out.println("attributes >> " + session.getAttributes());
		System.out.println(session.getId());
		System.out.println(session.getLocalAddress());
		System.out.println(session.getRemoteAddress());
		System.out.println(session.getUri());

		uri = session.getUri();
		if (uri == null)
			return;

		toks = uri.toString().split("/");
		if (toks == null)
			return;

		switch (toks[toks.length - 1]) {
		case "createChatRoom": { // 채팅방 방장이 채팅방을 생성하는 경우
			room = new ChatRoom();
			roomId = session.getId();
			room.addClient(session);

			if (ChatRoomManager.getInstance().addChatRoom(roomId, room)) { // 채팅방 매니저에 채팅방 추가
				String id = getIdFromHeader(session); // 사용자 아이디를 쿠키로 부터 받아온다.
				if (id == null) {
					System.out.println("id 쿠키가 존재하지 않습니다. 방을 추가할 수 없습니다.");
					return;
				}
				chatService.addUser(new ChatRoomDto(roomId, id)); // DB에 (room id, 방장 id) 추가. 초대 멤버들의 정보도 추가해야한다.
				sessionToRoomId.put(roomId, roomId);
				System.out.println("방이 생성되었습니다 >> " + room);
			} else {
				System.out.println("방을 추가할 수 없습니다.");
			}
		}
			break;

		case "chat": { // 채팅원이 채팅방에 참여하는 경우
			ChatRoomDto dto = new ChatRoomDto();
			dto.setUser_id(getIdFromHeader(session)); // 유저 아이디 설정
			roomId = chatService.getUserRoomId(dto); // 유저 아이디에 해당하는 채팅방 아이디 가져오기
			if (roomId != null) {
				room = ChatRoomManager.getInstance().getChatRoom(roomId);

				if (room.addClient(session) == false) {
					System.out.println("사용자가 허용 인원을 모두 채워 방에 사용자를 추가할 수 없습니다.");
					return;
				} else {
					sessionToRoomId.put(session.getId(), roomId);
					System.out.println("채팅방에 유저를 추가하였습니다.");
				}
			} else {
				System.out.println("유저에 해당하는 방이 없습니다.");
				session.close();
				return;
			
			}
		}
			break;
		}

		String sessionId = session.getId(); // 현재 세션 아이디를 가져온다
		room = ChatRoomManager.getInstance().getChatRoom(roomId);

		Message message = Message.builder().sender(sessionId).receiver("all").build();
		message.newConnect();

		room.getClients().values().forEach(s -> { // 해당 채딩방에만 메시지를 뿌려준다
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

		// ObjectMapper 생성
		ObjectMapper objectMapper = new ObjectMapper();
		// System.out.println(message.getPayload());
		// 문자열을 JSON으로 변환
		JsonNode jsonNode = objectMapper.readTree(message.getPayload());

		// JSON 출력
		System.out.println("JSON: " + jsonNode);

		String roomId = sessionToRoomId.get(session.getId());

		if (roomId == null) {
			System.out.println("메시지를 보낼 수 있는 채팅방이 존재하지 않습니다.");
			return;
		}
		// session에 해당하는 room을 찾아야 한다.
		if (session.isOpen()) {
			var clients = ChatRoomManager.getInstance().getChatRoom(roomId).getClients();

			clients.values().forEach(s -> {
				try {
					s.sendMessage(new TextMessage(jsonNode.toString()));
					System.out.println(s.getId() + "에게 메시지 : " + jsonNode.toString() + "을 전송하였습니다.");

				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		if (session.isOpen()) {
			session.close();
		}
		super.handleTransportError(session, exception);
	}

	/**
	 * 웹 소켓 연결 해제 후 수행할 동작 채팅방 관리자의 채팅방 목록 및 소속 채팅방 인원을 삭제한다.
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		

		System.out.println("연결 종료되었습니다");
		System.out.println(status);
		String sessionId = session.getId();
		sessions.remove(sessionId); // 세션 제거

		String id = session.getId();
		if (id == null)
			return;
		if(sessionToRoomId.get(id) == null)	// 방에 들어올 수 없는 인원이 들어왔다가 퇴장당한 경우
			return;
		if (sessionToRoomId.get(id).equals(id)) { // 방장이 나간 경우
			// 방 인원을 모두 제거하기
			ChatRoom room = ChatRoomManager.getInstance().getChatRoom(id);
			room.getClients().values().forEach(s -> {
				try {
					if (s.isOpen())
						s.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			room = null;
			ChatRoomManager.getInstance().deleteChatRoom(id);
		} else { // 채팅원이 나간 경우
			String roomId = sessionToRoomId.get(id);
			if (roomId == null)
				return;
			ChatRoom room = ChatRoomManager.getInstance().getChatRoom(roomId);
			room.removeClient(id);
		}
	}

}
