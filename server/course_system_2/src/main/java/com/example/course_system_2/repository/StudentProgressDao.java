package com.example.course_system_2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.course_system_2.entity.StudentProgress;

public interface StudentProgressDao extends JpaRepository<StudentProgress, Integer>{
	
	public List<StudentProgress> findByStudentId(String studentId);

	public StudentProgress findByCourseCodeAndStudentId(String courseCode, String studentId);
}
