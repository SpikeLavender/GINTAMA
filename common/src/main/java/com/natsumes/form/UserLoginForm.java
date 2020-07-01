package com.natsumes.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserLoginForm {

	//@NotBlank 用于 String 判断空格
	//@NotEmpty 用于集合
	//@NotNull
	@NotBlank
	private String username;

	@NotBlank
	private String password;
}
