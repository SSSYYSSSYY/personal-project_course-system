package com.example.course_system_2.dto;

import com.example.course_system_2.vo.StudentData;

public class StudentDetailRes {

	private String code;
	
	private String msg;
	
	private StudentData studentData;

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

	public StudentData getStudentData() {
		return studentData;
	}

	public void setStudentData(StudentData studentData) {
		this.studentData = studentData;
	}

	public StudentDetailRes(String code, String msg, StudentData studentData) {
		super();
		this.code = code;
		this.msg = msg;
		this.studentData = studentData;
	}
	
	
}
