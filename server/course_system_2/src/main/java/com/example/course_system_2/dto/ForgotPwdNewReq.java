package com.example.course_system_2.dto;

public class ForgotPwdNewReq {

	private String id;
	
	private String loginToken;
	
	private String newPwd;

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

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	public ForgotPwdNewReq() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ForgotPwdNewReq(String id, String loginToken, String newPwd) {
		super();
		this.id = id;
		this.loginToken = loginToken;
		this.newPwd = newPwd;
	}

	@Override
	public String toString() {
		return "ForgotPwdNewReq [id=" + id + ", loginToken=" + loginToken + ", newPwd=" + newPwd + "]";
	}
	
	
}
