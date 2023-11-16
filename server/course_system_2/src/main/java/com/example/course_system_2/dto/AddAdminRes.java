package com.example.course_system_2.dto;

import com.example.course_system_2.entity.AdminInfo;

public class AddAdminRes {

	private String code;
	
	private String msg;
	
	private AdminInfo adminInfo;

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

	public AdminInfo getAdminInfo() {
		return adminInfo;
	}

	public void setAdminInfo(AdminInfo adminInfo) {
		this.adminInfo = adminInfo;
	}

	public AddAdminRes(String code, String msg, AdminInfo adminInfo) {
		super();
		this.code = code;
		this.msg = msg;
		this.adminInfo = adminInfo;
	}
	
	
}
