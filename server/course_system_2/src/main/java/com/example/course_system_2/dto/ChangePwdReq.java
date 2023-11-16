package com.example.course_system_2.dto;

public class ChangePwdReq {

	private String id;
	
	private String oldPwd;
	
	private String newPwd;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOldPwd() {
		return oldPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	public ChangePwdReq() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ChangePwdReq(String id, String oldPwd, String newPwd) {
		super();
		this.id = id;
		this.oldPwd = oldPwd;
		this.newPwd = newPwd;
	}
	
	
}
