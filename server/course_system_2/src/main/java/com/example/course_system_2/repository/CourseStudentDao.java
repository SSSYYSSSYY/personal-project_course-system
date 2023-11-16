package com.example.course_system_2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.course_system_2.entity.CourseStudent;
import com.example.course_system_2.entity.StudentInfo;

public interface CourseStudentDao extends JpaRepository<CourseStudent, Integer>{

	@Query(value = "SELECT cst.student_id FROM course_student AS cst "
			+ " WHERE cst.course_code = :courseCode", nativeQuery = true)
	public List<String> getStudentIdListByCode(@Param("courseCode") String courseCode);
}
