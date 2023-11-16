package com.example.course_system_2.dto;

public class CheckLoginTokenRes {

	private String code;
	
	private String msg;
	
	private boolean login;

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

	public boolean isLogin() {
		return login;
	}

	public void setLogin(boolean login) {
		this.login = login;
	}

	public CheckLoginTokenRes(String code, String msg, boolean login) {
		super();
		this.code = code;
		this.msg = msg;
		this.login = login;
	}
	
	
	
	
}
