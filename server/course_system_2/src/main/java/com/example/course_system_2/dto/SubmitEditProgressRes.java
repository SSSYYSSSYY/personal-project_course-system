package com.example.course_system_2.dto;

import com.example.course_system_2.entity.StudentProgress;

public class SubmitEditProgressRes {

	private String code;
	
	private String msg;
	
	private StudentProgress studentProgress;

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

	public StudentProgress getStudentProgress() {
		return studentProgress;
	}

	public void setStudentProgress(StudentProgress studentProgress) {
		this.studentProgress = studentProgress;
	}

	public SubmitEditProgressRes(String code, String msg, StudentProgress studentProgress) {
		super();
		this.code = code;
		this.msg = msg;
		this.studentProgress = studentProgress;
	}
	
	
}
