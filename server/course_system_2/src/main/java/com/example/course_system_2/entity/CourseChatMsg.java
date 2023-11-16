package com.example.course_system_2.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "course_chat_msg")
public class CourseChatMsg {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "course_code")
	private String courseCode;
	
	@Column(name = "sender_id")
	private String senderId;
	
	@Column(name = "sender_identity")
	private String senderIdentity;
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "send_time")
	private LocalDateTime sendTime = LocalDateTime.now();
	
	//=======================

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

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getSenderIdentity() {
		return senderIdentity;
	}

	public void setSenderIdentity(String senderIdentity) {
		this.senderIdentity = senderIdentity;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getSendTime() {
		return sendTime;
	}

	public void setSendTime(LocalDateTime sendTime) {
		this.sendTime = sendTime;
	}
	
	//=======================

	public CourseChatMsg() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CourseChatMsg(String courseCode, String senderId, String senderIdentity, String content) {
		super();
		this.courseCode = courseCode;
		this.senderId = senderId;
		this.senderIdentity = senderIdentity;
		this.content = content;
	}
	
	
	
}
