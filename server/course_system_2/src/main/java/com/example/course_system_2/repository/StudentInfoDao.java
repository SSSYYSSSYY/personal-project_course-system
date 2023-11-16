package com.example.course_system_2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.course_system_2.entity.StudentInfo;

public interface StudentInfoDao extends JpaRepository<StudentInfo, String>{

	
}
