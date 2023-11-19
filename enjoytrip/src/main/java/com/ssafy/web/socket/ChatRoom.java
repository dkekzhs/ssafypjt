package com.ssafy.web.socket;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.socket.WebSocketSession;

public class ChatRoom {
	private ChatRoom instance = null;
	private Map<String, WebSocketSession> clients = null;
	private final int maxClientSize = 10;
	
	
	public Map<String, WebSocketSession> getClients() {
		return clients;
	}

	ChatRoom () {
		clients =  new ConcurrentHashMap<String, WebSocketSession>();
	}
	
	public ChatRoom getInstance() {
		if(instance == null) {
			instance = new ChatRoom();
		}
		return instance;
	}
	
	public Boolean addClient(WebSocketSession client) {
		if(clients.size() >= maxClientSize)
			return false;
		
		String key = client.getId();
		clients.put(key, client);
		
		return true;
	}
	
	public void removeClient(String clientId) {
		clients.remove(clientId);
	}

}
