package com.example.course_system_2.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.course_system_2.constants.RtnCode;
import com.example.course_system_2.dto.AddCourseReq;
import com.example.course_system_2.dto.AddCourseRes;
import com.example.course_system_2.dto.CourseDataRes;
import com.example.course_system_2.dto.EditCourseRes;
import com.example.course_system_2.dto.SelectCourseRes;
import com.example.course_system_2.dto.SubmitEditProgressRes;
import com.example.course_system_2.entity.CourseInfo;
import com.example.course_system_2.entity.CourseStatement;
import com.example.course_system_2.entity.CourseStudent;
import com.example.course_system_2.entity.StudentCourse;
import com.example.course_system_2.entity.StudentInfo;
import com.example.course_system_2.entity.StudentProgress;
import com.example.course_system_2.repository.CourseInfoDao;
import com.example.course_system_2.repository.CourseStatementDao;
import com.example.course_system_2.repository.CourseStudentDao;
import com.example.course_system_2.repository.StudentCourseDao;
import com.example.course_system_2.repository.StudentInfoDao;
import com.example.course_system_2.repository.StudentProgressDao;
import com.example.course_system_2.service.ifs.CourseService;
import com.example.course_system_2.vo.CourseData;

@Service
public class CourseServiceImpl implements CourseService{

	@Autowired
	private CourseInfoDao courseInfoDao;
	
	@Autowired
	private CourseStatementDao courseStatementDao;
	
	@Autowired
	private CourseStudentDao courseStudentDao;
	
	@Autowired
	private StudentInfoDao studentInfoDao;
	
	@Autowired
	private StudentCourseDao studentCourseDao;
	
	@Autowired
	private StudentProgressDao studentProgressDao;
	
	private boolean isInfoOk(String code, String outline, String instructor, String summary, String details, String city) {
		if(!StringUtils.hasText(code) || !StringUtils.hasText(outline) || !StringUtils.hasText(instructor) || 
			!StringUtils.hasText(summary) || !StringUtils.hasText(details) || !StringUtils.hasText(city)) {
			return false;
		}
		return true;
	}
	
	private boolean isStatementOk(List<String> statementList) {
		for(String s : statementList) {
			if(!StringUtils.hasText(s)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public AddCourseRes addCourse(AddCourseReq addCourseReq) {
		
		if(addCourseReq == null) {
			System.out.println("addCourseReq為null");
			return new AddCourseRes(RtnCode.DATA_ERROR.getCode(), RtnCode.DATA_ERROR.getMsg(), null, null);
		}
//		if(addCourseReq.getCourseInfo() == null) {
//			System.out.println("courseInfo為null");
//			return new AddCourseRes(RtnCode.DATA_ERROR.getCode(), RtnCode.DATA_ERROR.getMsg(), null, null);
//		}
//		
//		if(addCourseReq.getStatementList() == null) {
//			System.out.println("statementList為null");
//			return new AddCourseRes(RtnCode.DATA_ERROR.getCode(), RtnCode.DATA_ERROR.getMsg(), null, null);
//		}
		if(courseInfoDao.findByCode(addCourseReq.getCourseInfo().getCode()) != null) {
			return new AddCourseRes(RtnCode.DATA_EXISTED.getCode(), RtnCode.DATA_EXISTED.getMsg(), null, null);
		}
		
		if(!isInfoOk(addCourseReq.getCourseInfo().getCode(), addCourseReq.getCourseInfo().getOutline(), 
			addCourseReq.getCourseInfo().getInstructor(), addCourseReq.getCourseInfo().getSummary(), addCourseReq.getCourseInfo().getDetails(), addCourseReq.getCourseInfo().getCity())) {
			return new AddCourseRes(RtnCode.DATA_ERROR.getCode(), RtnCode.DATA_ERROR.getMsg(), null, null);
		}
		
		if(!isStatementOk(addCourseReq.getStatementList())) {
			return new AddCourseRes(RtnCode.DATA_ERROR.getCode(), RtnCode.DATA_ERROR.getMsg(), null, null);
		}
		
		CourseInfo infoRes = courseInfoDao.save(addCourseReq.getCourseInfo());
		List<String> statementRes = new ArrayList<>();
		
		for(String s : addCourseReq.getStatementList()) {
			CourseStatement statementData = new CourseStatement(addCourseReq.getCourseInfo().getCode(), s);
			courseStatementDao.save(statementData);
			statementRes.add(s);
		}
		
		return new AddCourseRes(RtnCode.SUCCESSFUL.getCode(), RtnCode.SUCCESSFUL.getMsg(), infoRes, statementRes);
		
		
		
	}

	@Override
	public List<CourseInfo> getAllCourses() {
		
		List<CourseInfo> res = courseInfoDao.findAll();
		
		return res;
	}

	@Override
	public CourseDataRes getCourseDetailByCode(String code) {
		if(!StringUtils.hasText(code)) {
			return new CourseDataRes(RtnCode.DATA_ERROR.getCode(), RtnCode.DATA_ERROR.getMsg(), null);
		}
		
		CourseInfo info = courseInfoDao.findByCode(code);
		if(info == null) {
			return new CourseDataRes(RtnCode.DATA_NOT_FOUND.getCode(), RtnCode.DATA_NOT_FOUND.getMsg(), null);
		}
		List<String> statementList = courseStatementDao.getStatementByCode(code);
		List<String> studentIdList = courseStudentDao.getStudentIdListByCode(code);
		
		List<StudentInfo> studentInfoList = new ArrayList<>();
		
		for(String studentId : studentIdList) {
			Optional<StudentInfo> res = studentInfoDao.findById(studentId);
			studentInfoList.add(res.get());
		}
		
		CourseData courseData = new CourseData(info, statementList, studentInfoList);
		
		return new CourseDataRes(RtnCode.SUCCESSFUL.getCode(), RtnCode.SUCCESSFUL.getMsg(), courseData);
	}

	@Override
	public EditCourseRes editCourse(String courseCode, String editAdmin, String outline, String summary, String details, String instructor, 
			String city, LocalDateTime startDate, LocalDateTime endDate, boolean isOffered) {
		
		if(!StringUtils.hasText(courseCode) || !StringUtils.hasText(outline) || !StringUtils.hasText(summary) || !StringUtils.hasText(details)
			|| !StringUtils.hasText(instructor) || !StringUtils.hasText(city)) {
			return new EditCourseRes(RtnCode.DATA_ERROR.getCode(), RtnCode.DATA_ERROR.getMsg(), null);
		}
		CourseInfo targetCourse = courseInfoDao.findByCode(courseCode);
		
		if(targetCourse == null) {
			return new EditCourseRes(RtnCode.DATA_NOT_FOUND.getCode(), RtnCode.DATA_NOT_FOUND.getMsg(), null);
		}
		
		LocalDateTime currentDatetime = LocalDateTime.now();
		
		targetCourse.setOutline(outline);
		targetCourse.setSummary(summary);
		targetCourse.setDetails(details);
		targetCourse.setInstructor(instructor);
		targetCourse.setCity(city);
		
		targetCourse.setStartDate(startDate);
		targetCourse.setEndDate(endDate);
		targetCourse.setEditAdmin(editAdmin);
		targetCourse.setEditDate(currentDatetime);
		targetCourse.setOffered(isOffered);
		
		courseInfoDao.save(targetCourse);
		return new EditCourseRes(RtnCode.SUCCESSFUL.getCode(), RtnCode.SUCCESSFUL.getMsg(), targetCourse);
	}

	@Override
	public SelectCourseRes selectCourse(String studentId, String courseCode, int weekday, int timeslot) {
		
		if(!StringUtils.hasText(studentId) || !StringUtils.hasText(courseCode)) {
			return new SelectCourseRes(RtnCode.DATA_ERROR.getCode(), RtnCode.DATA_ERROR.getMsg(), null, null);
		}
		
		CourseInfo c = courseInfoDao.findByCode(courseCode);
		List<String> statements = courseStatementDao.getStatementByCode(courseCode);
		
		
		if(c == null || !studentInfoDao.existsById(studentId)) {
			return new SelectCourseRes(RtnCode.DATA_NOT_FOUND.getCode(), RtnCode.DATA_NOT_FOUND.getMsg(), null, null);
		}
		
		//獲得該學生所選的所有課程
		List<StudentCourse> studentCourses = studentCourseDao.findByStudentId(studentId);
		//判斷是否有衝堂
		for(StudentCourse sc : studentCourses) {
			if(sc.getCourseWeekday() == weekday && sc.getCourseTimeslot() == timeslot) {
				//weekday跟timeslot都相同代表衝堂，回傳錯誤碼
				return new SelectCourseRes(RtnCode.DATA_EXISTED.getCode(), RtnCode.DATA_EXISTED.getMsg(), null, null);
			}
		}
		
		Optional<StudentInfo> res = studentInfoDao.findById(studentId);
		StudentInfo targetStudent = res.get();
		
		CourseStudent courseStudent = new CourseStudent(courseCode, studentId);
		StudentCourse targetCourse = new StudentCourse(studentId, courseCode, weekday, timeslot);
		StudentProgress progress = new StudentProgress(studentId, targetStudent.getName(), courseCode, c.getOutline(), statements.get(0));
		
		courseStudentDao.save(courseStudent);
		studentCourseDao.save(targetCourse);
		studentProgressDao.save(progress);
		
		
		return new SelectCourseRes(RtnCode.SUCCESSFUL.getCode(), RtnCode.SUCCESSFUL.getMsg(), c.getCode(), c.getOutline());
	}

	@Override
	public SubmitEditProgressRes editProgress(String courseCode, String studentId, String courseOutline,
			String courseStatement, boolean understood, String status, String feedback) {
		StudentProgress targetProgress = studentProgressDao.findByCourseCodeAndStudentId(courseCode, studentId);
		if(targetProgress == null) {
			return new SubmitEditProgressRes(RtnCode.DATA_NOT_FOUND.getCode(), RtnCode.DATA_NOT_FOUND.getMsg(), null);
		}
		targetProgress.setCourseStatement(courseStatement);
		targetProgress.setUnderstood(understood);
		targetProgress.setStatus(status);
		targetProgress.setFeedback(feedback);
		
		StudentProgress res = studentProgressDao.save(targetProgress);
		
		return new SubmitEditProgressRes(RtnCode.SUCCESSFUL.getCode(), RtnCode.SUCCESSFUL.getMsg(), res);
	}



	


}
