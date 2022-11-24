package com.springboot.Loginpractice.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class pageController {
	
	@GetMapping({"/","/index"})
	public String loadIndex () {
		return "index";
	}
	
	
	@GetMapping("/admin")
	public String loadAdmin () {
		return "admin";
	}
	
	@GetMapping("/mypage")
	public String loadMyPage() {
		return "mypage";
	}
}
