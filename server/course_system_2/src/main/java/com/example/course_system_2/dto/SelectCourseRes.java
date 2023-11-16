package com.example.course_system_2.dto;

import com.example.course_system_2.entity.CourseInfo;
import com.example.course_system_2.vo.CourseData;

public class SelectCourseRes {
	
	private String code;
	
	private String msg;
	
	private String courseCode;
	
	private String courseOutline;


	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getCourseOutline() {
		return courseOutline;
	}

	public void setCourseOutline(String courseOutline) {
		this.courseOutline = courseOutline;
	}

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

	public SelectCourseRes(String code, String msg, String courseCode, String courseOutline) {
		super();
		this.code = code;
		this.msg = msg;
		this.courseCode = courseCode;
		this.courseOutline = courseOutline;
	}





	
	

}
