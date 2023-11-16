package com.example.course_system_2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.course_system_2.entity.CourseStatement;

public interface CourseStatementDao extends JpaRepository<CourseStatement, Integer>{

	@Query(value = "SELECT cs.course_statement FROM course_statement AS cs "
			+ " WHERE cs.course_code = :courseCode", nativeQuery = true)
	public List<String> getStatementByCode(@Param("courseCode") String courseCode);
	

}
