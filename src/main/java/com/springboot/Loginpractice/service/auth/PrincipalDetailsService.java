package com.springboot.Loginpractice.service.auth;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springboot.Loginpractice.domain.user.User;
import com.springboot.Loginpractice.domain.user.UserRepository;
import com.springboot.Loginpractice.web.dto.auth.SignupReqDto;

import lombok.RequiredArgsConstructor;
//두번째 loginProcessingUrl을 커스텀 해주는 서비스
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
	
	private final UserRepository userRepository; // DB에 저장된 user정보랑 loadUserByUsername의 username이랑 비교 해야되서 가지고 와야됨
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User userEntity = null;
		// DB에 저장된 user정보와 페이지에 입력한 정보가 다르다면 Exception을 날려 준다
		try {
			userEntity = userRepository.findUserByUserName(username);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UsernameNotFoundException(username);
		}
		
		if(userEntity == null) {
			throw new UsernameNotFoundException(username + "사용자 이름은 사용 할 수 없습니다.");
		}
		
		// 1. 아무 문제가 없다면 PrincipalDtails의 user로 보내 권한, 비밀번호, 아이디를 모두 체크한다.
		
		return new PrincipalDetails(userEntity);
		// 2. 권한, 비밀번호, 아이디를 다 체크한 후 로그인이 가능해진다.
	}
	
	public boolean addUser(SignupReqDto signupReqDto) throws Exception {
		
		return userRepository.save(signupReqDto.toEntity())>0;
	}
	
}
