package com.example.course_system_2.dto;

public class LogoutRes {

	private String code;
	
	private String msg;

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

	public LogoutRes(String code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}
	
	
}
