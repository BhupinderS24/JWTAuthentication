package com.zversal.auth.security;

import com.zversal.auth.config.JwtSecurityConfig;
import com.zversal.auth.model.JwtUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class JwtValidator {
	//private String secret = "zversal";
	//private JwtSecurityConfig config= new JwtSecurityConfig();
	
	@Autowired
	private Environment environment;
	
	public JwtUser validate(String token) {
		System.out.println("Validator");
		JwtUser jwtUser = null;
		try {
			Claims body = Jwts.parser().setSigningKey(/* "zversal" */ /* config.getSecretKey() */ environment.getProperty("security.jwt.token.secret-key")).parseClaimsJws(token).getBody();
			jwtUser = new JwtUser();
			jwtUser.setUserName(body.getSubject());
			jwtUser.setEmail((String) body.get("email"));
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return jwtUser;
	}
}
