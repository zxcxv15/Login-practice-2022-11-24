package com.springboot.Loginpractice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@EnableWebSecurity // 기존의 상속받은 WebSecurityConfigurerAdapter를 비활성화하고 현재 Security를 쓰겠다.
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	//첫번째 Security 설정
	
	// 비밀번호 암호화
	@Bean
	public BCryptPasswordEncoder PasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable(); //CSRF Token 사용하지 않겠다.(간단한 로그인만 함)
		http.authorizeRequests()// 요청이 들어왔을 때 인증들을 거쳐라
			//인증들
			.antMatchers("/","/index","/mypage/**") 		// 1. 우리가 지정한 요청
			.authenticated()				// 2. 인증이 필요하다.
			.anyRequest()					// 다른 모든요청은
			.permitAll()					// 모두 접근 권한을 부여하겠다.
			.and()
											// 인증이 필요하니 formLogin을 해라
			.formLogin()					// 로그인 방식은 form 로그인을 사용한다
			.loginPage("/admin")			// 로그인 페이지는 해당 get 요청을 통해 접근한다.
			.loginProcessingUrl("/admin")	// 로그인 요청(post 요청)
			.defaultSuccessUrl("/");
	}
	
}
