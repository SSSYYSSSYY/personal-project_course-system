package com.example.course_system_2.vo;

public class CourseSimpleInfo {

	private String code;
	
	private String outline;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getOutline() {
		return outline;
	}

	public void setOutline(String outline) {
		this.outline = outline;
	}

	public CourseSimpleInfo(String code, String outline) {
		super();
		this.code = code;
		this.outline = outline;
	}
	
	
}
