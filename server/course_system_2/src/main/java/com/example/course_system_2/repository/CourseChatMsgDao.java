package com.example.course_system_2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.course_system_2.entity.CourseChatMsg;

public interface CourseChatMsgDao extends JpaRepository<CourseChatMsg, Integer>{

	public List<CourseChatMsg> findByCourseCode(String courseCode);
}
