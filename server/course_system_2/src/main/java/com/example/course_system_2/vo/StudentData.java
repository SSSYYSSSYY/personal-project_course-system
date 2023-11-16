package com.example.course_system_2.vo;

import java.time.LocalDateTime;
import java.util.List;

import com.example.course_system_2.entity.CourseInfo;
import com.example.course_system_2.entity.StudentInfo;
import com.example.course_system_2.entity.StudentProgress;

public class StudentData {

	//基本資料
	private StudentInfo studentInfo;
	
	//學生的課表
	private CourseInfo[][] schedule = {
			{null, null, null, null},//代表星期一
			{null, null, null, null},//代表星期二
			{null, null, null, null},//代表星期三
			{null, null, null, null},//代表星期四
			{null, null, null, null},//代表星期五
	}; 
	
	//學生的進度
	private List<StudentProgress> progressList;
	
	//========================

	public StudentInfo getStudentInfo() {
		return studentInfo;
	}

	public void setStudentInfo(StudentInfo studentInfo) {
		this.studentInfo = studentInfo;
	}

	public CourseInfo[][] getSchedule() {
		return schedule;
	}

	public void setSchedule(CourseInfo[][] schedule) {
		this.schedule = schedule;
	}

	public List<StudentProgress> getProgressList() {
		return progressList;
	}

	public void setProgressList(List<StudentProgress> progressList) {
		this.progressList = progressList;
	}


	
	//========================

	public StudentData(StudentInfo studentInfo, CourseInfo[][] schedule, List<StudentProgress> progressList) {
		super();
		this.studentInfo = studentInfo;
		this.schedule = schedule;
		this.progressList = progressList;
	}
	
	
	
}
