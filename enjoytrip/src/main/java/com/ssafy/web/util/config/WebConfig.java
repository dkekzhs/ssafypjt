package com.ssafy.web.util.config;

import com.ssafy.web.util.web.AuthInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	@Autowired
	AuthInterceptor AuthInterceptor;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// TODO Auto-generated method stub

		registry.addMapping("/**")
				.allowedOrigins("http://localhost:5173", "http://localhost:5174",
						"http://70.12.107.143:5173/","http://70.12.107.143:5174/")
				.allowedMethods("POST","GET")
				.allowCredentials(true);

	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(AuthInterceptor).addPathPatterns("/board/write","/board/view/**");
	}




}
