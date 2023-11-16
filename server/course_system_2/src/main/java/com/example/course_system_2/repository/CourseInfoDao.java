package com.example.course_system_2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.course_system_2.entity.CourseInfo;

public interface CourseInfoDao extends JpaRepository<CourseInfo, Integer>{

	public CourseInfo findByCode(String code);
}
