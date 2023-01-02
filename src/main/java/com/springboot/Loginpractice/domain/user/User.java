package com.springboot.Loginpractice.domain.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder // 빌더패션 자동 생성
@NoArgsConstructor // 기본생성자
@AllArgsConstructor // 전체생성자
@Data // getter, setter
// user 객체
public class User {
	private int user_code;
	private String user_name;
	private String user_email;
	private String user_id;
	private String oauth2_id;
	private String user_password;
	private String user_roles;
	private String user_provider;
	private String user_profile_img;
	private String user_address;
	private String user_phone;
	private int user_gender;

	
	public List<String> getUserRoles() {
		//null이거나 비어있다면 아무 권한이 없다.
		if(user_roles == null || user_roles.isBlank()) {
			return new ArrayList<String>();
		}
		// ex) ROLE_USER,ROLE_ADMIN -> split:  "," 단위로 짤라서 String 배열을 만들어 준다.
		// 만든 배열을 리스트로 리턴 받겠다.
		return Arrays.asList(user_roles.replaceAll(" ", "").split(","));
		
		//replaceAll 해당 띄어쓰기 찾아서 전부다 공백(문자열)으로 바꾸고, 쉼표로 짤라라
	}
}
