package com.example.course_system_2.dto;

public class SubmitEditProgressReq {

	private String courseCode;
	
	private String studentId;
	
	private String courseOutline;
	
	private String courseStatement;
	
	private boolean understood;
	
	private String status;
	
	private String feedback;

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getCourseOutline() {
		return courseOutline;
	}

	public void setCourseOutline(String courseOutline) {
		this.courseOutline = courseOutline;
	}

	public String getCourseStatement() {
		return courseStatement;
	}

	public void setCourseStatement(String courseStatement) {
		this.courseStatement = courseStatement;
	}

	public boolean isUnderstood() {
		return understood;
	}

	public void setUnderstood(boolean understood) {
		this.understood = understood;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public SubmitEditProgressReq() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SubmitEditProgressReq(String courseCode, String studentId, String courseOutline, String courseStatement,
			boolean understood, String status, String feedback) {
		super();
		this.courseCode = courseCode;
		this.studentId = studentId;
		this.courseOutline = courseOutline;
		this.courseStatement = courseStatement;
		this.understood = understood;
		this.status = status;
		this.feedback = feedback;
	}
	
	
}
