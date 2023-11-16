package com.example.course_system_2.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "student_info")
public class StudentInfo {

	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "identity")
	private String identity = "student";
	
	@Column(name = "pwd")
	private String pwd;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "birthday")
	private LocalDateTime birthday;
	
	@Column(name = "create_date")
	private LocalDateTime createDate = LocalDateTime.now();
	
	@Column(name = "is_enable")
	private boolean isEnable = false;
	
	//========================

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDateTime getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDateTime birthday) {
		this.birthday = birthday;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public boolean isEnable() {
		return isEnable;
	}

	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}
	
	//========================
	
	public StudentInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public StudentInfo(String id, String pwd, String name, String email, LocalDateTime birthday) {
		super();
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.email = email;
		this.birthday = birthday;
		
		this.createDate = LocalDateTime.now();
		
		//identity固定為student
		//isEnable預設為false
		//createDate自動抓當前時間
	}


}
