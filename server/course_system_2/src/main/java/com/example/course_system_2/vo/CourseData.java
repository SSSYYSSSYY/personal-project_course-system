package com.example.course_system_2.vo;

import java.time.LocalDateTime;
import java.util.List;

import com.example.course_system_2.entity.CourseInfo;
import com.example.course_system_2.entity.CourseStatement;
import com.example.course_system_2.entity.StudentInfo;

public class CourseData {

	//基本資訊
	private CourseInfo courseInfo;
	
	//課程細項list
	private List<String> statementList;
	
	//選修此課堂的學生list
	private List<StudentInfo> studentList;
	
	//==================

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

	public List<StudentInfo> getStudentList() {
		return studentList;
	}

	public void setStudentList(List<StudentInfo> studentList) {
		this.studentList = studentList;
	}
	
	//==================

	public CourseData(CourseInfo courseInfo, List<String> statementList, List<StudentInfo> studentList) {
		super();
		this.courseInfo = courseInfo;
		this.statementList = statementList;
		this.studentList = studentList;
	}
	
	//==================

	
}
