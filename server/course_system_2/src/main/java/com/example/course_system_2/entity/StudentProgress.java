package com.example.course_system_2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "student_progress")
public class StudentProgress {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "student_id")
	private String studentId;
	
	@Column(name = "student_name")
	private String studentName;

	@Column(name = "course_code")
	private String courseCode;
	
	@Column(name = "course_outline")
	private String courseOutline;
	
	@Column(name = "course_statement")
	private String courseStatement;
	
	@Column(name = "is_understood")
	private boolean understood;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "feedback")
	private String feedback;
	
	//========================
	
	
	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public int getId() {
		return id;
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

	public String getCourseOutline() {
		return courseOutline;
	}

	public void setCourseOutline(String courseOutline) {
		this.courseOutline = courseOutline;
	}

	public String getCourseStatement() {
		return courseStatement;
	}

	public void setCourseStatement(String courseStatement) {
		this.courseStatement = courseStatement;
	}



	public boolean isUnderstood() {
		return understood;
	}

	public void setUnderstood(boolean understood) {
		this.understood = understood;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}


	
	//========================
	
	

	//學生選了課之後，自己的進度管理頁會自動多一筆該課程的進度資料(只有code outline 第一項statement)
	public StudentProgress(String studentId, String studentName, String courseCode, String courseOutline, String courseStatement) {
		super();
		this.studentId = studentId;
		this.studentName = studentName;
		this.courseCode = courseCode;
		this.courseOutline = courseOutline;
		this.courseStatement = courseStatement;
	}
	//之後再由學生手動修改，修改用set方法
	//選課之後自動出現的進度資料，isUnderstood為false，status為「未有進度」，feedback為「目前未回報」

	public StudentProgress() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
