package com.example.course_system_2.dto;

import com.example.course_system_2.entity.StudentInfo;

public class AddStudentRes {
	
	private String code;
	
	private String msg;
	
	private StudentInfo studentInfo;

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

	public StudentInfo getStudentInfo() {
		return studentInfo;
	}

	public void setStudentInfo(StudentInfo studentInfo) {
		this.studentInfo = studentInfo;
	}

	public AddStudentRes(String code, String msg, StudentInfo studentInfo) {
		super();
		this.code = code;
		this.msg = msg;
		this.studentInfo = studentInfo;
	}
	
	

}
