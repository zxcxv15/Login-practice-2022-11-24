package com.springboot.Loginpractice.service.auth;

import org.springframework.stereotype.Service;

import com.springboot.Loginpractice.domain.user.UserRepository;
import com.springboot.Loginpractice.web.dto.auth.UsernameCheckReqDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
	
	private final UserRepository userRepository;

	@Override
	public boolean checkUsername(UsernameCheckReqDto usernameCheckReqDto) throws Exception {
		
		return userRepository.findUserByUserName(usernameCheckReqDto.getUsername()) == null;
		// null이면 회원 가입이 가능하다
	}

	@Override
	public boolean signup() {

		return false;
	}

	
}
