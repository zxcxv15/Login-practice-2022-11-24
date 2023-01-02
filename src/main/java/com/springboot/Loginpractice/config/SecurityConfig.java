package com.springboot.Loginpractice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.springboot.Loginpractice.config.auth.AuthFailureHandler;
import com.springboot.Loginpractice.service.auth.PrincipalOauth2UserService;

import lombok.RequiredArgsConstructor;


@EnableWebSecurity // 기존의 상속받은 WebSecurityConfigurerAdapter를 비활성화하고 현재 Security를 쓰겠다.
@Configuration // IoC 컨테이너에 등록
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	//첫번째 Security 설정 및 userDetaileService 만들고, 핸들러 만들어 주기.
	//두번째 DB와 연결
	
	// 생성자 생성
	public final PrincipalOauth2UserService principalOauth2UserService;
	
	// 비밀번호 암호화
	@Bean
	public BCryptPasswordEncoder PasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable(); //CSRF Token 사용하지 않겠다.
		http.authorizeRequests()// 요청이 들어왔을 때 인증들을 거쳐라
			
		
			.antMatchers("/","/index","/mypage/**") 	// 1. 우리가 지정한 요청
			 //.access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')") 권한
			.authenticated()							// 2. 인증이 필요하다.
			
			.anyRequest()								// 하지만 우리가 지정한 요청 외 다른 모든요청은
			.permitAll()								// 모두 접근 권한을 부여하겠다.(그냥 로그인 된다.)
			.and()
														// 3. 인증이 필요하니 formLogin을 해라
			.formLogin()								// 4. 로그인 방식은 form 로그인을 사용한다
			.loginPage("/auth/signin")					// 5. 로그인 페이지는 해당 get 요청을 통해 접근한다.
			.loginProcessingUrl("/auth/signin")			// 6. 로그인 요청(post 요청) -> PrincipalDetailsService로 가서 로그인 가능한지 비교해보겠지
			.failureHandler(new AuthFailureHandler())	// 7. 로그인 시에 예외가 있다면 낚아채서 처리할 수 있는 핸들러
			//기존 시큐리티 로그인에서 밑에 추가 해줌
			.and()
			
			.oauth2Login()
			.userInfoEndpoint()							//
			.userService(principalOauth2UserService) 	//PrincipalOauth2UserServiced의 user가 와야 하기때문에 생성자 만들어서 가지고 오면 됨.
			
			.and()
			
			.defaultSuccessUrl("/index");  				// 8. 로그인 성공시 설정한 페이지로 보냄

			
	}
	
}
