package com.example.course_system_2.dto;

public class CheckLoginTokenReq {

	private String id;
	
	private String loginToken;

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

	public CheckLoginTokenReq() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CheckLoginTokenReq(String id, String loginToken) {
		super();
		this.id = id;
		this.loginToken = loginToken;
	}
	
	
}
