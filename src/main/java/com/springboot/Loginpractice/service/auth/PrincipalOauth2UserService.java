package com.springboot.Loginpractice.service.auth;

import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.springboot.Loginpractice.domain.user.User;
import com.springboot.Loginpractice.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j //Log 사용할 수 있다.
@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
	/*
	 *1. [DefaultOAuth2UserService]상속하면 loadUser 오버라이드 해서 사용가능
	 * /
	
	/* [PrincipalOauth2UserService]
	 *  OAuth2User의 정보를 우리 서버 database에 등록하는 것이 목표!!
	 */
	private final UserRepository userRepository;


	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		String provider = null;
		
		/*
		 *  [supper.loadUser(userRequest)]
		 *  엔드포인트 결과 즉, OAuth2User 정보를 가진 객체를 리턴
		 */
		OAuth2User oAuth2User = super.loadUser(userRequest);

		/*
		 *  [ClientRegistration]
		 *  Provider 정보(클라이언트 아이디, 클라이언트 시크릿, 클라이언트 네임)
		 */
		
		ClientRegistration clientRegistration = userRequest.getClientRegistration();
		
		/*
		 *  [getAttributes] -> Map형태임
		 *  실제 프로필 정보를 가져온다.
		 */
		Map<String, Object> attributes = oAuth2User.getAttributes();
		log.error(">>>>> ClientRegistration: {}", clientRegistration);
		log.error(">>>>> attributes: {}", attributes);
		
		provider = clientRegistration.getClientName(); //getClientName에 따라 구분함(Google,Naver)
		
		User user = getOAuth2User(provider, attributes); // 로그인 되어져야 할 실제 객체(앞의 작업이 유저 객체를 만들기 위함이다.)
		
		return new PrincipalDetails(user, attributes); // PrincipalDetails: 우리가 만든 유저 정보
	}
	
	//id 생성
	private User getOAuth2User(String provider, Map<String, Object> attributes) throws OAuth2AuthenticationException {
		String oauth2_id = null;
		String id = null;
		
		User user = null;
		
		Map<String, Object> response = null;
		
		if(provider.equalsIgnoreCase("google")) {
			response = attributes;
			id = (String) response.get("sub");
			
		}else if(provider.equalsIgnoreCase("naver")) {
			response = (Map<String, Object>) attributes.get("response");
			id = (String) response.get("id");
			
		}else {
			throw new OAuth2AuthenticationException("provider Error!");
		}
		
		oauth2_id = provider + "_" + id; // 아이디 만들기
		
		// DB에서 아이디 찾아주기
		try {
			user = userRepository.findOAuth2UserByUsername(oauth2_id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new OAuth2AuthenticationException("DATABASE Error!");
		}
		
		// 유저가 null(없다면)이 떳다면 새로운 User 생성
		if(user == null) {
			user = User.builder()
					.user_name((String) response.get("name")) //response -> map이 Object이기 떄문에 다운캐스팅을 해야 사용가능하다.
					.user_email((String) response.get("email"))
					.user_id(oauth2_id)
					.oauth2_id(oauth2_id)
					.user_password(new BCryptPasswordEncoder().encode(id))
					.user_roles("ROLE_USER")
					.user_provider(provider)
					.build();
			
			// 만들어서 회원가입
			try {
				userRepository.save(user);
				user = userRepository.findOAuth2UserByUsername(oauth2_id);
			} catch (Exception e) {
				e.printStackTrace();
				throw new OAuth2AuthenticationException("DATABASE Error!");
			}
		}
		
		return user;
	}
	
}
