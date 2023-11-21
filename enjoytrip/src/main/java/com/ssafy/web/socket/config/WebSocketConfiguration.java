package com.ssafy.web.socket.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.ssafy.web.socket.SocketInterceptor;
import com.ssafy.web.socket.WebSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {
	
	@Autowired
	SocketInterceptor SocketInterceptor;
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry
			.addHandler(webSocketHandler(), "/chat")
			.setAllowedOriginPatterns("*").addInterceptors(SocketInterceptor);
		
		registry
		.addHandler(webSocketHandler(), "/chat")
		.setAllowedOriginPatterns("*")
		.withSockJS().setInterceptors(SocketInterceptor);
		
		registry
		.addHandler(webSocketHandler(), "/createChatRoom")
		.setAllowedOriginPatterns("*").addInterceptors(SocketInterceptor);
		
		registry
		.addHandler(webSocketHandler(), "/createChatRoom")
		.setAllowedOriginPatterns("*")
		.withSockJS().setInterceptors(SocketInterceptor);
	}

	@Bean
	public WebSocketHandler webSocketHandler() {
		return new WebSocketHandler();
	}

	
}
