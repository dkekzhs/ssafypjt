package com.ssafy.web.socket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.socket.TextMessage;

public class ChatRoomManager {

	private static ChatRoomManager instance = null; 
	private Map<String, ChatRoom> manager = null;
	private final int maxRoomSize = 10;
	private final int maxClientSize = 10;
	
	private ChatRoomManager() {
		manager = new ConcurrentHashMap<String,ChatRoom>();
	}
	public static ChatRoomManager getInstance() {
		if(instance == null) {
			instance = new ChatRoomManager();
		}
		return instance;
		
	}
	
	// 메시지 뿌리는 거
	public void boardcast(String roomId, String message) {
		ChatRoom room = manager.get(roomId);
		
		if(room == null)
			return;
		
		room.getClients().values().forEach(client -> {
			try {
				if (room.getClients().containsKey(client.getId())) {
					client.sendMessage(new TextMessage(message.toString()));
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	// chat room 추가
	public void addChatRoom(String roomId, ChatRoom room) {
		manager.put(roomId, room);
	}
	
	// chat room 삭제
	public void deleteChatRoom(String roomId) {
		if(manager.containsKey(roomId)) 
			manager.remove(roomId);
	}
	
	public ChatRoom getChatRoom(String roomId) {
		if(manager.containsKey(roomId)) {
			return manager.get(roomId);
		}
		return null;
	}
}
