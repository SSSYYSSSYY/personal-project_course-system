package com.example.course_system_2.dto;

public class ForgotPwdReq {
	
	private String id;
	
	private String email;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ForgotPwdReq() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ForgotPwdReq(String id, String email) {
		super();
		this.id = id;
		this.email = email;
	}

	@Override
	public String toString() {
		return "ForgotPwdReq [id=" + id + ", email=" + email + "]";
	}
	
	

}
