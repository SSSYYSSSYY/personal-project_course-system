package com.example.course_system_2.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "admin_info")
public class AdminInfo {
	
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "identity")
	private String identity = "admin";
	
	@Column(name = "pwd")
	private String pwd;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "permission")
	private int permission;
	//0 -> 超級管理員，1 -> 普通管理員 
	//用Enum
	
	@Column(name = "birthday")
	private LocalDateTime birthday;
	
	@Column(name = "create_date")
	private LocalDateTime createDate = LocalDateTime.now();
	
	@Column(name = "is_enable")
	private boolean isEnable;
	
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

	public int getPermission() {
		return permission;
	}

	public void setPermission(int permission) {
		this.permission = permission;
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
	
	

	public AdminInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AdminInfo(String id, String pwd, String name, String email, int permission, LocalDateTime birthday) {
		super();
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.email = email;
		this.permission = permission;
		this.birthday = birthday;
		
//		this.createDate = LocalDateTime.now();
		
		//identity固定為admin
		//isEnable預設為false
		//createDate自動抓當前時間
	}
	

	
	
	
}
