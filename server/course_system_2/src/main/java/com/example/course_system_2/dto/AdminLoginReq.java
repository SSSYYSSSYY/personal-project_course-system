package com.example.course_system_2.dto;

public class AdminLoginReq {

	private String adminId;
	
	private String pwd;

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public AdminLoginReq() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AdminLoginReq(String adminId, String pwd) {
		super();
		this.adminId = adminId;
		this.pwd = pwd;
	}
	
	
}
