package com.zversal.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/zversal/auth")
public class ZversalController {

	@GetMapping
	public String hello() {
		return "Zversal";
	}
}
