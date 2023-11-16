package com.example.course_system_2.constants;

public enum Permission {
	
	SUPER(0, "super admin"),
	NORMAL(1, "normal admin");

	private int code;
	
	private String des;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	private Permission(int code, String des) {
		this.code = code;
		this.des = des;
	}
	
	
}
