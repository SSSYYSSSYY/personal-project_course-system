package com.example.course_system_2.dto;

import java.util.List;

import com.example.course_system_2.entity.CourseInfo;
import com.example.course_system_2.entity.CourseStatement;

public class AddCourseRes {

	private String code;
	
	private String msg;
	
	private CourseInfo courseInfo;
	
	private List<String> statementList;

	public List<String> getStatementList() {
		return statementList;
	}

	public void setStatementList(List<String> statementList) {
		this.statementList = statementList;
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

	public CourseInfo getCourseInfo() {
		return courseInfo;
	}

	public void setCourseInfo(CourseInfo courseInfo) {
		this.courseInfo = courseInfo;
	}

	public AddCourseRes(String code, String msg, CourseInfo courseInfo, List<String> statementList) {
		super();
		this.code = code;
		this.msg = msg;
		this.courseInfo = courseInfo;
		this.statementList = statementList;
	}


	
	
}
