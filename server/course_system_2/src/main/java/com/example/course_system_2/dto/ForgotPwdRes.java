package com.example.course_system_2.dto;

public class ForgotPwdRes {

	private String code;
	
	private String msg;
	
	private String id;
	
	private String loginToken;
	
	private String encodedVerifyCode;
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoginToken() {
		return loginToken;
	}

	public void setLoginToken(String loginToken) {
		this.loginToken = loginToken;
	}



	public String getEncodedVerifyCode() {
		return encodedVerifyCode;
	}

	public void setEncodedVerifyCode(String encodedVerifyCode) {
		this.encodedVerifyCode = encodedVerifyCode;
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

	public ForgotPwdRes(String code, String msg, String id, String loginToken, String encodedVerifyCode) {
		super();
		this.code = code;
		this.msg = msg;
		this.id = id;
		this.loginToken = loginToken;
		this.encodedVerifyCode = encodedVerifyCode;
	}





	
}
