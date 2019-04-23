package com.zversal.auth.security;

import com.zversal.auth.model.JwtUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class JwtGenerator {
	@Autowired
	private Environment env;
	public String generate(JwtUser jwtUser) {
		Claims claims = Jwts.claims().setSubject(jwtUser.getUserName());
		claims.put("email", jwtUser.getEmail());

		return Jwts.builder().setExpiration(new Date(System.currentTimeMillis() + 5000))
				.setClaims(claims).signWith(SignatureAlgorithm.HS512,env.getProperty("security.jwt.token.secret-key")).compact();
	}
}
