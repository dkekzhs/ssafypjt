package com.ssafy.web.socket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.ssafy.web.socket.WebSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry
			.addHandler(webSocketHandler(), "/chat")
			.setAllowedOriginPatterns("*");
		
		registry
		.addHandler(webSocketHandler(), "/chat")
		.setAllowedOriginPatterns("*")
		.withSockJS();
	}

	@Bean
	public WebSocketHandler webSocketHandler() {
		return new WebSocketHandler();
	}

	
}
