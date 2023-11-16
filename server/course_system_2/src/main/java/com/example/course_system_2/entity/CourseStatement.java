package com.example.course_system_2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "course_statement")
public class CourseStatement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "course_code")
	private String courseCode;
	
	@Column(name = "course_statement")
	private String courseStatement;
	
	//========================

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getCourseStatement() {
		return courseStatement;
	}

	public void setCourseStatement(String courseStatement) {
		this.courseStatement = courseStatement;
	}
	
	//========================
	
	public CourseStatement(String courseCode, String courseStatement) {
		super();
		this.courseCode = courseCode;
		this.courseStatement = courseStatement;
	}
}
