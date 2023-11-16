package com.example.course_system_2.dto;

import com.example.course_system_2.entity.AdminInfo;

public class AdminLoginRes {

	private String code;
	
	private String msg;
	
	private String loginToken;
	
	private Integer permission;

	public Integer getPermission() {
		return permission;
	}

	public void setPermission(Integer permission) {
		this.permission = permission;
	}

	public String getLoginToken() {
		return loginToken;
	}

	public void setLoginToken(String loginToken) {
		this.loginToken = loginToken;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public AdminLoginRes(String code, String msg, String loginToken, Integer permission) {
		super();
		this.code = code;
		this.msg = msg;
		this.loginToken = loginToken;
		this.permission = permission;
	}




	
}
