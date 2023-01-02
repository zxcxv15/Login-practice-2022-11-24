package com.springboot.Loginpractice.service.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.springboot.Loginpractice.domain.user.User;

import lombok.Data;

@Data
public class PrincipalDetails implements UserDetails, OAuth2User{
	private static final long serialVersionUID = 1L;
	
	private User user; //DB의 있는 정보를 가지고 있는 user.
	private Map<String, Object> attribute;
	
	public PrincipalDetails(User user) {
		this.user = user;
	}
	public PrincipalDetails(User user,Map<String, Object> attribute) {
		this.user = user;
		this.attribute = attribute;
	}

	// User 객체에서 리스트로 만들어 둔 권한을 컬렉션으로 바꿔 하나씩 비교한다.
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();

		user.getUserRoles().forEach(role -> {
			grantedAuthorities.add(() -> role);
		});
		
		return grantedAuthorities;
	}

	@Override
	public String getPassword() {
		return user.getUser_password();
	}

	@Override
	public String getUsername() {
		return user.getUser_id();
	}

	/*
	 *  계정 만료 여부
	 *  true: 만료 안됨
	 *  false: 만료
	 */
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/*
	 *  계정 잠김 여부
	 *  true: 잠기지 않음
	 *  false: 잠김
	 */
	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/*
	 *  비밀번호 만료 여부
	 *  true: 만료 안됨
	 *  false: 만료됨
	 */
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/*
	 *  사용자 활성화 여부
	 *  true: 활성화
	 *  false: 비활성화
	 */
	
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	//oauth2
	@Override
	public Map<String, Object> getAttributes() {
		return attribute;
	}

	@Override
	public String getName() {
		return user.getUser_name();
	}

}
