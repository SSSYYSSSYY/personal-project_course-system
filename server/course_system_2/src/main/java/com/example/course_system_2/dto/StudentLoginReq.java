package com.example.course_system_2.dto;

public class StudentLoginReq {

	private String studentId;
	
	private String pwd;

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public StudentLoginReq() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StudentLoginReq(String studentId, String pwd) {
		super();
		this.studentId = studentId;
		this.pwd = pwd;
	}
	
	
}
