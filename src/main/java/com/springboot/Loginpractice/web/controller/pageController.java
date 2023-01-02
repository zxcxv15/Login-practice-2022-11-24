package com.springboot.Loginpractice.web.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.springboot.Loginpractice.service.auth.PrincipalDetails;

@Controller
public class pageController {
	//@AuthenticationPrincipal 사용하면 PrincipalDetails이라는 객체를 받아올 수 있다. 왜? 로그인 했을 떄 정보를 저기에다 넣어 두었으까
	// 세션영역에서 PrincipalDetails를 꺼내오는 어노테이션
	@GetMapping({"/", "/index"})
	public String loadIndex(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		//html에 addAttribute되는 부분
		model.addAttribute("principal", principalDetails); // model 서버 사이드 렌더링
		//					키 				벨류
		return "index";
	}
	
	@GetMapping("/auth/signin")
	public String loadSignin() {
		return "auth/signin";
	}
	
	@GetMapping("/auth/signup")
	public String loadSignup() {
		return "auth/signup";
	}
	
	@GetMapping("/mypage")
	public String loadMyPage() {
		return "mypage";
	}
		
}