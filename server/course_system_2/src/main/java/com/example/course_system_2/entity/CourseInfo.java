package com.example.course_system_2.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "course_info")
public class CourseInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "outline")
	private String outline;
	
	@Column(name = "instructor")
	private String instructor;
	
	@Column(name = "summary")
	private String summary;
	
	@Column(name = "details")
	private String details;
	
	@Column(name = "weekday")
	private int weekday;
	
	@Column(name = "timeslot")
	private int timeslot;
	
	@Column(name = "start_date")
	private LocalDateTime startDate;
	
	@JsonFormat(timezone = "GMT+8")
	@Column(name = "end_date")
	private LocalDateTime endDate;
	
	@Column(name = "is_offered")
	private boolean isOffered;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "create_date")
	private LocalDateTime createDate = LocalDateTime.now();
	
	@Column(name = "edit_date")
	private LocalDateTime editDate;
	
	@Column(name = "edit_admin")
	private String editAdmin;
	
	//========================

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public String getInstructor() {
		return instructor;
	}

	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public int getWeekday() {
		return weekday;
	}

	public void setWeekday(int weekday) {
		this.weekday = weekday;
	}

	public int getTimeslot() {
		return timeslot;
	}

	public void setTimeslot(int timeslot) {
		this.timeslot = timeslot;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public boolean isOffered() {
		return isOffered;
	}

	public void setOffered(boolean isOffered) {
		this.isOffered = isOffered;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public LocalDateTime getEditDate() {
		return editDate;
	}

	public void setEditDate(LocalDateTime editDate) {
		this.editDate = editDate;
	}

	public String getEditAdmin() {
		return editAdmin;
	}

	public void setEditAdmin(String editAdmin) {
		this.editAdmin = editAdmin;
	}

	public CourseInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CourseInfo(String code, String outline, String instructor, String summary, String details, int weekday, int timeslot,
			LocalDateTime startDate, LocalDateTime endDate, String city, String editAdmin, boolean isOffered) {
		super();
		this.code = code;
		this.outline = outline;
		this.instructor = instructor;
		this.summary = summary;
		this.details = details;
		this.weekday = weekday;
		this.timeslot = timeslot;
		this.startDate = startDate;
		this.endDate = endDate;
		this.city = city;
		this.editAdmin = editAdmin;
		this.isOffered = isOffered;
		
//		this.createDate = LocalDateTime.now();
//		this.editDate = LocalDateTime.now();
		
		//id為自增
		//createDate跟editDate自動抓當前時間
		//isOffered預設true
	}
	

	
	
}
