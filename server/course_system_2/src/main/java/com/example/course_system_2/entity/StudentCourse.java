package com.example.course_system_2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "student_course")
public class StudentCourse {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "student_id")
	private String studentId;
	
	@Column(name = "course_code")
	private String courseCode;
	
	@Column(name = "course_weekday")
	private int courseWeekday;
	
	@Column(name = "course_timeslot")
	private int courseTimeslot;
	
	//========================
	
	

	public int getId() {
		return id;
	}

	public int getCourseWeekday() {
		return courseWeekday;
	}

	public void setCourseWeekday(int courseWeekday) {
		this.courseWeekday = courseWeekday;
	}

	public int getCourseTimeslot() {
		return courseTimeslot;
	}

	public void setCourseTimeslot(int courseTimeslot) {
		this.courseTimeslot = courseTimeslot;
	}

	public void setId(int id) {
		this.id = id;
	}

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


	
	//========================
	
	

	public StudentCourse(String studentId, String courseCode, int courseWeekday, int courseTimeslot) {
		super();
		this.studentId = studentId;
		this.courseCode = courseCode;
		this.courseWeekday = courseWeekday;
		this.courseTimeslot = courseTimeslot;
	}

	public StudentCourse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
