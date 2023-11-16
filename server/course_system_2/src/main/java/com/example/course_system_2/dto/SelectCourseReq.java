package com.example.course_system_2.dto;

public class SelectCourseReq {

	private String studentId;
	
	private String courseCode;
	
	private int weekday;
	
	private int timeslot;

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public int getWeekday() {
		return weekday;
	}

	public void setWeekday(int weekday) {
		this.weekday = weekday;
	}

	public int getTimeslot() {
		return timeslot;
	}

	public void setTimeslot(int timeslot) {
		this.timeslot = timeslot;
	}

	public SelectCourseReq() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SelectCourseReq(String studentId, String courseCode, int weekday, int timeslot) {
		super();
		this.studentId = studentId;
		this.courseCode = courseCode;
		this.weekday = weekday;
		this.timeslot = timeslot;
	}
	
	
	
}
