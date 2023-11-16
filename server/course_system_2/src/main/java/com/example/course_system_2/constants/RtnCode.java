package com.example.course_system_2.constants;

public enum RtnCode {
	
	SUCCESSFUL("200", "successful!"),
	DATA_ERROR("400", "data error!"),
	DATA_EXISTED("400", "data existed!"),
	DATA_NOT_FOUND("404", "data not found!"),
	ACCOUNT_EXISTED("400", "account existed!"),
	ACCOUNT_NOT_FOUND("404", "account not found!"),
	VERIFY_ERROR("400", "verify error!"),
	VERIFY_FAILED("403", "verify failed!");
	
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

	private RtnCode(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	
}
