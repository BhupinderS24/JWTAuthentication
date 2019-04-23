package com.zversal.auth.controller;

import com.zversal.auth.repository.UsersRepository;
import com.zversal.auth.model.JwtUser;
import com.zversal.auth.security.JwtGenerator;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

	@Autowired
	private UsersRepository repo;
	private JwtGenerator jwtGenerator;

	private JwtUser jwtUser = new JwtUser();

	public TokenController(JwtGenerator jwtGenerator) {
		this.jwtGenerator = jwtGenerator;
	}

	@RequestMapping(value="/token",method=RequestMethod.POST)
	public String generate(@RequestBody JwtUser jwt) throws JSONException {
		String user = jwt.getUserName();
		jwtUser = repo.findByUserName(user);
		if (jwtUser != null) {
			String token = jwtGenerator.generate(jwtUser);
			return token;
		} else {
			return "invalid User - No token";
		}
	}
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public String register(@RequestBody JwtUser user,HttpServletResponse response) {
		if(repo.findByUserName(user.getUserName())!=null)
		{
			response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
			return "UserName is Already Exist";
		}
		else {
		repo.save(user);		
		return "Successfully Registered";
		}
	}
}
