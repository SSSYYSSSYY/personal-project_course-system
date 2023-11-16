package com.example.course_system_2.dto;

import com.example.course_system_2.vo.StudentData;

public class StudentLoginRes {
	
	private String code;
	
	private String msg;
	
	private String loginToken;

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

	public StudentLoginRes(String code, String msg, String loginToken) {
		super();
		this.code = code;
		this.msg = msg;
		this.loginToken = loginToken;
	}



}
