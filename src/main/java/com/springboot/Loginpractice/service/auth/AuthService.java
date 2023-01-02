package com.springboot.Loginpractice.service.auth;

import com.springboot.Loginpractice.web.dto.auth.UsernameCheckReqDto;

public interface AuthService {

	public boolean checkUsername(UsernameCheckReqDto usernameCheckReqDto) throws Exception;
	
	public boolean signup() throws Exception;
}
