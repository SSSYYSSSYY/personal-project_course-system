package com.example.course_system_2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.course_system_2.entity.StudentCourse;

public interface StudentCourseDao extends JpaRepository<StudentCourse, Integer>{

	public List<StudentCourse> findByStudentId(String studentId);
}
