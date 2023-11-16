package com.example.course_system_2.dto;

import java.time.LocalDateTime;

public class EditCourseReq {
	
	private String editAdmin;

	private String outline;
	
	private String summary;
	
	private String details;
	
	private String instructor;
	
	private String city;
	
	private LocalDateTime startDate;
	
	private LocalDateTime endDate;
	
	private boolean offered;

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	

	public boolean isOffered() {
		return offered;
	}

	public void setOffered(boolean offered) {
		this.offered = offered;
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

	public String getEditAdmin() {
		return editAdmin;
	}

	public void setEditAdmin(String editAdmin) {
		this.editAdmin = editAdmin;
	}

	public String getOutline() {
		return outline;
	}

	public void setOutline(String outline) {
		this.outline = outline;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getInstructor() {
		return instructor;
	}

	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public EditCourseReq() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EditCourseReq(String editAdmin, String outline, String summary, String details, String instructor,
			String city, LocalDateTime startDate, LocalDateTime endDate, boolean offered) {
		super();
		this.editAdmin = editAdmin;
		this.outline = outline;
		this.summary = summary;
		this.details = details;
		this.instructor = instructor;
		this.city = city;
		this.startDate = startDate;
		this.endDate = endDate;
		this.offered = offered;
	}

	@Override
	public String toString() {
		return "EditCourseReq [editAdmin=" + editAdmin + ", outline=" + outline + ", summary=" + summary + ", details="
				+ details + ", instructor=" + instructor + ", city=" + city + ", startDate=" + startDate + ", endDate="
				+ endDate + ", isOffered=" + offered + "]";
	}








	
}
