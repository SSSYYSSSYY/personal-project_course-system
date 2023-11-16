package com.example.course_system_2.dto;

public class CheckVerifyCodeReq {

	private String id;
	
	private String loginToken;
	
	private String inputVerifyCode;
	
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

	public String getInputVerifyCode() {
		return inputVerifyCode;
	}

	public void setInputVerifyCode(String inputVerifyCode) {
		this.inputVerifyCode = inputVerifyCode;
	}

	public String getEncodedVerifyCode() {
		return encodedVerifyCode;
	}

	public void setEncodedVerifyCode(String encodedVerifyCode) {
		this.encodedVerifyCode = encodedVerifyCode;
	}

	public CheckVerifyCodeReq() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CheckVerifyCodeReq(String id, String loginToken, String inputVerifyCode, String encodedVerifyCode) {
		super();
		this.id = id;
		this.loginToken = loginToken;
		this.inputVerifyCode = inputVerifyCode;
		this.encodedVerifyCode = encodedVerifyCode;
	}
	
	
}
