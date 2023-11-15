package com.ssafy.web.util;

import java.util.Date;

import com.ssafy.web.member.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtTokenProvider {

	public static String createAccessToken(User m,String salt) {
		String token="";
		
		Claims claims=Jwts.claims();
		claims.put("id",m.getId());
		claims.put("name", m.getPw());
		
		Date now=new Date();
		
		token=Jwts.builder()
		.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
		.setClaims(claims) //저장 데이터
		.setIssuedAt(now)//발행시간
		.setExpiration(new Date(now.getTime()+(1000L*60*3)))//만료시간 1분
		.signWith(SignatureAlgorithm.HS256, salt)
		.compact();
		
		return token;
	}
	
	public static String createRefreshToken(String id, String salt) {
		String token="";
		
		Claims claims=Jwts.claims();
		claims.put("id",id);//String
		//claims.put("member", m);//Object
		
		Date now=new Date();
		
		token=Jwts.builder()
		.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
		.setClaims(claims) //저장 데이터
		.setIssuedAt(now)//발행시간
		.setExpiration(new Date(now.getTime()+(1000L*60*30)))//만료시간 5분
		.signWith(SignatureAlgorithm.HS256, salt)
		.compact();
		
		return token;
	}
	
	// Jwt Token의 유효성 및 만료 기간 검사합니다
    public static boolean validateToken(String jwtToken, String salt) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(salt).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
    
    // Jwt Token에서 데이터를 전달 합니다.
    public static Claims getInformation(String token, String salt) {
        Claims claims =Jwts.parser().setSigningKey(salt).parseClaimsJws(token).getBody();
        return claims;
    }
	
//	public static void main(String[] args) {
//		Member m=new Member("a", "전은수", "b", "a", "ssafy.com", new Date());
//		String accessToken=createAccessToken(m,"salt");
//		String refreshToken=createRefreshToken(m.getUser_id(),"salt");
//		System.out.println(accessToken);
//		System.out.println(refreshToken);
//	}

}