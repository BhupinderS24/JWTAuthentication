package com.zversal.auth.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import com.zversal.auth.model.JwtUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtGenerator {
	@Autowired
	private Environment env;

	public String generate(JwtUser jwtUser) {
		Claims claims = Jwts.claims().setSubject(jwtUser.getUserName());
		claims.put("email", jwtUser.getEmail());
		String tokenExpirationInMilliSecondsString = env.getProperty("security.authentication.jwt.token-validity");
		long tokenExpirationInMilliSecondsStringLong = Long.parseLong(tokenExpirationInMilliSecondsString);
		return Jwts.builder().setClaims(claims)
				.signWith(SignatureAlgorithm.HS512, env.getProperty("security.authentication.jwt.secret-key"))
				.setExpiration(new Date(System.currentTimeMillis() + tokenExpirationInMilliSecondsStringLong))
				.compact();
	}
}
