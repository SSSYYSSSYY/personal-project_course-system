package com.example.course_system_2.dto;

import com.example.course_system_2.vo.CourseData;

public class CourseDataRes {

	private String code;
	
	private String msg;
	
	private CourseData courseData;

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

	public CourseData getCourseData() {
		return courseData;
	}

	public void setCourseData(CourseData courseData) {
		this.courseData = courseData;
	}

	public CourseDataRes(String code, String msg, CourseData courseData) {
		super();
		this.code = code;
		this.msg = msg;
		this.courseData = courseData;
	}
	
	
}
