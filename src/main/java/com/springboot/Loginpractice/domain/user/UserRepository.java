package com.springboot.Loginpractice.domain.user;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepository {
	
	//회원가입 시 정보 저장
	public int save(User user) throws Exception;
	
	//로그인 시 validation
	public User findUserByUserName(String username) throws Exception;
	//oauth2 로그인 
	public User findOAuth2UserByUsername(String oauth2_id) throws Exception;
	

}
