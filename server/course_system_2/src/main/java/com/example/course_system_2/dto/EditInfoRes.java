package com.example.course_system_2.dto;

public class EditInfoRes {

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

	public EditInfoRes(String code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}
	
	
}
