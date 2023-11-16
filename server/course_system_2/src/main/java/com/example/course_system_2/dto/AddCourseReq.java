package com.example.course_system_2.dto;

import java.util.List;

import com.example.course_system_2.entity.CourseInfo;
import com.example.course_system_2.entity.CourseStatement;

public class AddCourseReq {
	
	private CourseInfo courseInfo;
	
	private List<String> statementList;

	public CourseInfo getCourseInfo() {
		return courseInfo;
	}

	public void setCourseInfo(CourseInfo courseInfo) {
		this.courseInfo = courseInfo;
	}

	public List<String> getStatementList() {
		return statementList;
	}

	public void setStatementList(List<String> statementList) {
		this.statementList = statementList;
	}

	public AddCourseReq() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AddCourseReq(CourseInfo courseInfo, List<String> statementList) {
		super();
		this.courseInfo = courseInfo;
		this.statementList = statementList;
	}


	
	
}
