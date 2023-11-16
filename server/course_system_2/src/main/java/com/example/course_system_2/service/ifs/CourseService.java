package com.example.course_system_2.service.ifs;

import java.time.LocalDateTime;
import java.util.List;

import com.example.course_system_2.dto.AddCourseReq;
import com.example.course_system_2.dto.AddCourseRes;
import com.example.course_system_2.dto.CourseDataRes;
import com.example.course_system_2.dto.EditCourseRes;
import com.example.course_system_2.dto.SelectCourseRes;
import com.example.course_system_2.dto.SubmitEditProgressRes;
import com.example.course_system_2.entity.CourseInfo;
import com.example.course_system_2.entity.StudentCourse;
import com.example.course_system_2.vo.CourseData;

public interface CourseService {

	public AddCourseRes addCourse(AddCourseReq addCourseReq);
	
	public List<CourseInfo> getAllCourses();
	
	public CourseDataRes getCourseDetailByCode(String code);
	
	//需要的資料：code editAdmin
	//能修改的資料：outline summary instructor city startDate endDate isOffered
	public EditCourseRes editCourse(String courseCode, String editAdmin, String outline, String summary, String details, String instructor, 
			String city, LocalDateTime startDate, LocalDateTime endDate, boolean isOffered);
	
	//讓學生選課的邏輯：
	//先搜尋StudentCourse中有沒有studentId和weekday和timeslot都相同的資料，若有的話代表衝堂，回傳錯誤碼
	//CourseStudent中新增一筆資料：課程code、學生id (流水號PK)
	//StudentCourse中新增
	public SelectCourseRes selectCourse(String studentId, String courseCode, int weekday, int timeslot);
	
	public SubmitEditProgressRes editProgress(String courseCode, String studentId, String courseOutline, 
			String courseStatement, boolean understood, String status, String feedback);
	
}
