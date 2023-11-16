package com.example.course_system_2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.course_system_2.dto.AddCourseReq;
import com.example.course_system_2.dto.AddCourseRes;
import com.example.course_system_2.dto.CourseDataRes;
import com.example.course_system_2.dto.EditCourseReq;
import com.example.course_system_2.dto.EditCourseRes;
import com.example.course_system_2.dto.SelectCourseReq;
import com.example.course_system_2.dto.SelectCourseRes;
import com.example.course_system_2.dto.SubmitEditProgressReq;
import com.example.course_system_2.dto.SubmitEditProgressRes;
import com.example.course_system_2.entity.CourseInfo;
import com.example.course_system_2.entity.CourseStatement;
import com.example.course_system_2.repository.CourseStatementDao;
import com.example.course_system_2.service.ifs.CourseService;

@CrossOrigin
@RestController
public class CourseController {
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private CourseStatementDao courseStatementDao;
	
	
	//獲得所有課程基本資料的list
	@GetMapping(value = "/courses")
	public List<CourseInfo> getAllCourses(){
		return courseService.getAllCourses();
	}
	
	//新增課程資訊
	@PostMapping(value = "/add_course")
	public AddCourseRes addCourse(@RequestBody AddCourseReq addCourseReq) {
		return courseService.addCourse(addCourseReq);
	}
	
	//獲得特定課程的所有資料
	@GetMapping(value = "/courses/{courseCode}")
	public CourseDataRes getCourseDetail(@PathVariable String courseCode) {
		return courseService.getCourseDetailByCode(courseCode);
	}
	
	@GetMapping(value = "/statement/{courseCode}")
	public List<String> getStatementListByCode(@PathVariable String courseCode){
		System.out.println("尋找特定課程的項目list");
		List<String> res = courseStatementDao.getStatementByCode(courseCode);
		for(String s : res) {
			System.out.println(s);
		}
		return res;
	}
	
	//修改課程資料邏輯：
	//用code找課程(code不得修改)，來修改其他資料
	//在前端讓code的部分保持不變
	//只能修改基本資料，不能修改項目(因為會影響到學生進度資料)
	@PatchMapping(value = "/courses/{courseCode}")
	public EditCourseRes editCourseInfo(@PathVariable String courseCode, @RequestBody EditCourseReq editCourseReq) {
		System.out.println(editCourseReq.toString());
		return courseService.editCourse(courseCode, editCourseReq.getEditAdmin(), editCourseReq.getOutline(), 
				editCourseReq.getSummary(), editCourseReq.getDetails(), editCourseReq.getInstructor(), editCourseReq.getCity(), editCourseReq.getStartDate(), 
				editCourseReq.getEndDate(), editCourseReq.isOffered());
		
	}
	
	@PostMapping(value = "/select")
	public SelectCourseRes selectCourse(@RequestBody SelectCourseReq selectCourseReq) {
		return courseService.selectCourse(selectCourseReq.getStudentId(), selectCourseReq.getCourseCode(), 
				selectCourseReq.getWeekday(), selectCourseReq.getTimeslot());
	}
	
	@PostMapping(value = "/editProgress")
	public SubmitEditProgressRes editProgress(@RequestBody SubmitEditProgressReq req) {
		return courseService.editProgress(req.getCourseCode(), req.getStudentId(), req.getCourseOutline(), 
				req.getCourseStatement(), req.isUnderstood(), req.getStatus(), req.getFeedback());
	}

}
