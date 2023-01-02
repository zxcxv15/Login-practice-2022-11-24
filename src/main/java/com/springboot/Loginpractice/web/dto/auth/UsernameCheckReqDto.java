package com.springboot.Loginpractice.web.dto.auth;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UsernameCheckReqDto {
	@NotBlank 
	@Size(max = 16, min = 4, message = "4자 이상 16자 이하만 가능합니다.")
	private String username;
}
