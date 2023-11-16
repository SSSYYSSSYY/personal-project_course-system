package com.example.course_system_2.dto;

import java.time.LocalDateTime;

public class EditInfoReq {
	
	private String identity;
	
	private String id;
	
	private String name;
	
	private String email;
	
    private LocalDateTime birthday;

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public EditInfoReq() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EditInfoReq(String identity, String id, String name, String email, LocalDateTime birthday) {
		super();
		this.identity = identity;
		this.id = id;
		this.name = name;
		this.email = email;
		this.birthday = birthday;
	}
    
    
}
