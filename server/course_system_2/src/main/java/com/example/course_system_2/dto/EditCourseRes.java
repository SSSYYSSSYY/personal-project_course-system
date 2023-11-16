package com.example.course_system_2.dto;

import java.util.List;

import com.example.course_system_2.entity.CourseInfo;

public class EditCourseRes {
	
	//用code找課程(code不得修改)，來修改其他資料
	//在前端讓code的部分保持不變
	//只能修改基本資料，不能修改項目(因為會影響到學生進度資料)
	
	private String code;
	
	private String msg;
	
	private CourseInfo courseInfo;

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

	public EditCourseRes(String code, String msg, CourseInfo courseInfo) {
		super();
		this.code = code;
		this.msg = msg;
		this.courseInfo = courseInfo;
	}
	


}
