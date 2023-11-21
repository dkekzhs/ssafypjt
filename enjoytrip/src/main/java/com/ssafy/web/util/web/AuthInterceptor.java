package com.ssafy.web.util.web;

import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ssafy.web.member.model.MemberDto;

@Component
public class AuthInterceptor implements HandlerInterceptor {
	 
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if(CorsUtils.isPreFlightRequest(request)){
			return true;
		}
		response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET");
		response.setHeader("Access-Control-Allow-Credentials", "true");

    	HttpSession session =  request.getSession(false);
    	System.out.println("asdasdasdsadsad"+ session);
    	if(session != null) {
    		System.out.println(session);
    		MemberDto dto = (MemberDto)session.getAttribute("user_info");
    		if(dto == null) throw new AuthException("로그인해주세요");
    		String user_id = dto.getUser_id();
    		if(!"".equals(user_id) && user_id != null) {
    			return true;
    		}



    		
    	}
		System.out.println("인터셉터 발생 ");
    	System.out.println("인터셉터 발생 "); 
    	System.out.println("인터셉터 발생 ");
    	throw new AuthException("로그인해주세요");
    }

    
}