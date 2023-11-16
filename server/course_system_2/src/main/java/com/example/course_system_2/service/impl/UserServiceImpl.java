package com.example.course_system_2.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.course_system_2.constants.RtnCode;
import com.example.course_system_2.dto.AddAdminRes;
import com.example.course_system_2.dto.AddStudentRes;
import com.example.course_system_2.dto.AdminLoginRes;
import com.example.course_system_2.dto.ChangePwdRes;
import com.example.course_system_2.dto.EditCourseRes;
import com.example.course_system_2.dto.EditInfoRes;
import com.example.course_system_2.dto.ForgotPwdRes;
import com.example.course_system_2.dto.LogoutRes;
import com.example.course_system_2.dto.StudentDetailRes;
import com.example.course_system_2.dto.StudentLoginRes;
import com.example.course_system_2.entity.AdminInfo;
import com.example.course_system_2.entity.CourseInfo;
import com.example.course_system_2.entity.StudentCourse;
import com.example.course_system_2.entity.StudentInfo;
import com.example.course_system_2.entity.StudentProgress;
import com.example.course_system_2.repository.AdminInfoDao;
import com.example.course_system_2.repository.CourseInfoDao;
import com.example.course_system_2.repository.StudentCourseDao;
import com.example.course_system_2.repository.StudentInfoDao;
import com.example.course_system_2.repository.StudentProgressDao;
import com.example.course_system_2.service.EmailService;
import com.example.course_system_2.service.ifs.UserService;
import com.example.course_system_2.vo.CourseSimpleInfo;
import com.example.course_system_2.vo.StudentData;

import net.bytebuddy.utility.RandomString;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private AdminInfoDao adminInfoDao;
	
	@Autowired
	private StudentInfoDao studentInfoDao;
	
	@Autowired
	private StudentCourseDao studentCourseDao;
	
	@Autowired
	private StudentProgressDao studentProgressDao;
	
	@Autowired
	private CourseInfoDao courseInfoDao;
	
	@Autowired
	private EmailService emailService;
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	public boolean isInfoOk(String id, String pwd, String name, String email) {
		
		if(!StringUtils.hasText(id) || !StringUtils.hasText(pwd) || !StringUtils.hasText(name) || !StringUtils.hasText(email)) {
			return false;
		}
		if(pwd.length() < 8) {
			return false;
		}

		return true;
	}

	@Override
	public AddAdminRes addAdmin(AdminInfo adminInfo) {
		if(!isInfoOk(adminInfo.getId(), adminInfo.getPwd(), adminInfo.getName(), adminInfo.getEmail()) || adminInfo.getPermission() > 1) {
			return new AddAdminRes(RtnCode.DATA_ERROR.getCode(), RtnCode.DATA_ERROR.getMsg(), null);
		}
		if(adminInfoDao.existsById(adminInfo.getId())) {
			return new AddAdminRes(RtnCode.ACCOUNT_EXISTED.getCode(), RtnCode.ACCOUNT_EXISTED.getMsg(), null);
		}

		String mailContent = "您現在可以登入至選課系統！\n帳號：" + adminInfo.getId() + "\n密碼：" + adminInfo.getPwd() + "\n登入後須先修改密碼才能啟用帳號。";
		
		emailService.sendEmail(adminInfo.getEmail(), "【選課系統】已為您創建管理者帳號", mailContent);
		
		adminInfo.setPwd(encoder.encode(adminInfo.getPwd()));
		
		AdminInfo res = adminInfoDao.save(adminInfo);
		
		return new AddAdminRes(RtnCode.SUCCESSFUL.getCode(), RtnCode.SUCCESSFUL.getMsg(), res);
	}

	@Override
	public AddStudentRes addStudent(StudentInfo studentInfo) {
		if(!isInfoOk(studentInfo.getId(), studentInfo.getPwd(), studentInfo.getName(), studentInfo.getEmail())) {
			return new AddStudentRes(RtnCode.DATA_ERROR.getCode(), RtnCode.DATA_ERROR.getMsg(), null);
		}
		if(studentInfoDao.existsById(studentInfo.getId())) {
			return new AddStudentRes(RtnCode.ACCOUNT_EXISTED.getCode(), RtnCode.ACCOUNT_EXISTED.getMsg(), null);
		}
		
		String mailContent = "您現在可以登入至選課系統！\n帳號：" + studentInfo.getId() + "\n密碼：" + studentInfo.getPwd() + "\n登入後須先修改密碼才能啟用帳號。";
		
		emailService.sendEmail(studentInfo.getEmail(), "【選課系統】已為您創建學生帳號", mailContent);
		
		studentInfo.setPwd(encoder.encode(studentInfo.getPwd()));
		
		StudentInfo res = studentInfoDao.save(studentInfo);
		
		return new AddStudentRes(RtnCode.SUCCESSFUL.getCode(), RtnCode.SUCCESSFUL.getMsg(), res);
	}

	@Override
	public StudentLoginRes studentLogin(String studentId, String pwd) {
		
		if(!StringUtils.hasText(studentId) || !StringUtils.hasText(pwd)) {
			return new StudentLoginRes(RtnCode.DATA_ERROR.getCode(), RtnCode.DATA_ERROR.getMsg(), null);
		}
	
		Optional<StudentInfo> res = studentInfoDao.findById(studentId);
		
		if(res.isEmpty()) {
			return new StudentLoginRes(RtnCode.ACCOUNT_NOT_FOUND.getCode(), RtnCode.ACCOUNT_NOT_FOUND.getMsg(), null);
		}
		
		StudentInfo student = res.get();
		
		//比對密碼
		if(!encoder.matches(pwd, student.getPwd())) {
			return new StudentLoginRes(RtnCode.VERIFY_ERROR.getCode(), RtnCode.VERIFY_ERROR.getMsg(), null);
		}

		
		return new StudentLoginRes(RtnCode.SUCCESSFUL.getCode(), RtnCode.SUCCESSFUL.getMsg(), encoder.encode(studentId));
	}
	
	@Override
	public StudentDetailRes getStudentDetail(String studentId, String loginToken) {
		
		if(!StringUtils.hasText(studentId) || !StringUtils.hasText(loginToken)) {
			return new StudentDetailRes(RtnCode.DATA_ERROR.getCode(), RtnCode.DATA_ERROR.getMsg(), null);
		}

		Optional<StudentInfo> res = studentInfoDao.findById(studentId);
		
		if(res.isEmpty()) {
			return new StudentDetailRes(RtnCode.ACCOUNT_NOT_FOUND.getCode(), RtnCode.ACCOUNT_NOT_FOUND.getMsg(), null);
		}
		if(!encoder.matches(studentId, loginToken)) {
			return new StudentDetailRes(RtnCode.DATA_ERROR.getCode(), RtnCode.DATA_ERROR.getMsg(), null);
		}
		
		StudentInfo student = res.get();
		
		//把studentCourse資料存成二維陣列
		List<StudentCourse> studentCourseList = studentCourseDao.findByStudentId(studentId);
		CourseInfo[][] schedule = {
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
		};
		
		for(StudentCourse sc : studentCourseList) {
			CourseInfo targetCourse = courseInfoDao.findByCode(sc.getCourseCode());
			schedule[sc.getCourseWeekday()][sc.getCourseTimeslot()] = targetCourse;
		}
		
		//獲得該學生的進度資料
		List<StudentProgress> progressList = studentProgressDao.findByStudentId(studentId);
		
		StudentData studentData = new StudentData(student, schedule, progressList);
		return new StudentDetailRes(RtnCode.SUCCESSFUL.getCode(), RtnCode.SUCCESSFUL.getMsg(), studentData);
	}

	@Override
	public AdminLoginRes adminLogin(String adminId, String adminPwd) {
		
		if(!StringUtils.hasText(adminId) || !StringUtils.hasText(adminPwd)) {
			return new AdminLoginRes(RtnCode.DATA_ERROR.getCode(), RtnCode.DATA_ERROR.getMsg(), null, null);
		}
		
		Optional<AdminInfo> res = adminInfoDao.findById(adminId);
		
		if(res.isEmpty()) {
			return new AdminLoginRes(RtnCode.ACCOUNT_NOT_FOUND.getCode(), RtnCode.ACCOUNT_NOT_FOUND.getMsg(), null, null);
		}
		
		AdminInfo admin = res.get();
		if(!encoder.matches(adminPwd, admin.getPwd())) {
			return new AdminLoginRes(RtnCode.VERIFY_ERROR.getCode(), RtnCode.VERIFY_ERROR.getMsg(), null, null);
		}
		
		return new AdminLoginRes(RtnCode.SUCCESSFUL.getCode(), RtnCode.SUCCESSFUL.getMsg(), encoder.encode(adminId), admin.getPermission());
	}

	@Override
	public LogoutRes logout() {
		// TODO Auto-generated method stub
		return null;
	}

	//新密碼的部分要多一欄確認密碼，前端要檢查兩個欄位是否相同
	@Override
	public ChangePwdRes studentChangePwd(String studentId, String oldPwd, String newPwd) {
		
		if(!StringUtils.hasText(studentId) || !StringUtils.hasText(oldPwd) || !StringUtils.hasText(newPwd) || newPwd.length() < 8) {
			return new ChangePwdRes(RtnCode.DATA_ERROR.getCode(), RtnCode.DATA_ERROR.getMsg());
		}
		Optional<StudentInfo> res = studentInfoDao.findById(studentId);
		
		if(res.isEmpty()) {
			return new ChangePwdRes(RtnCode.ACCOUNT_NOT_FOUND.getCode(), RtnCode.ACCOUNT_NOT_FOUND.getMsg());
		}
		
		StudentInfo student = res.get();
		
		if(!encoder.matches(oldPwd, student.getPwd())) {
			return new ChangePwdRes(RtnCode.VERIFY_ERROR.getCode(), RtnCode.VERIFY_ERROR.getMsg());
		}
		
		//舊密碼和新密碼不可相同
		if(newPwd.equals(oldPwd)) {
			return new ChangePwdRes(RtnCode.DATA_ERROR.getCode(), RtnCode.DATA_ERROR.getMsg());
		}
		
		student.setPwd(encoder.encode(newPwd));
		student.setEnable(true);
		studentInfoDao.save(student);
		
		return new ChangePwdRes(RtnCode.SUCCESSFUL.getCode(), RtnCode.SUCCESSFUL.getMsg());
	}

	@Override
	public ChangePwdRes adminChangePwd(String adminId, String oldPwd, String newPwd) {
		
		if(!StringUtils.hasText(adminId) || !StringUtils.hasText(oldPwd) || !StringUtils.hasText(newPwd) || newPwd.length() < 8) {
			return new ChangePwdRes(RtnCode.DATA_ERROR.getCode(), RtnCode.DATA_ERROR.getMsg());
		}
		
		Optional<AdminInfo> res = adminInfoDao.findById(adminId);
		
		if(res.isEmpty()) {
			return new ChangePwdRes(RtnCode.ACCOUNT_NOT_FOUND.getCode(), RtnCode.ACCOUNT_NOT_FOUND.getMsg());
		}
		
		AdminInfo admin = res.get();
		
		if(!encoder.matches(oldPwd, admin.getPwd())) {
			return new ChangePwdRes(RtnCode.VERIFY_ERROR.getCode(), RtnCode.VERIFY_ERROR.getMsg());
		}
		
		if(newPwd.equals(oldPwd)) {
			return new ChangePwdRes(RtnCode.DATA_ERROR.getCode(), RtnCode.DATA_ERROR.getMsg());
		}
		
		admin.setPwd(encoder.encode(newPwd));
		admin.setEnable(true);
		adminInfoDao.save(admin);
		
		return new ChangePwdRes(RtnCode.SUCCESSFUL.getCode(), RtnCode.SUCCESSFUL.getMsg());
	}

	@Override
	public ForgotPwdRes forgorPwdAdmin(String id, String email) {
		
		if(!StringUtils.hasText(id) || !StringUtils.hasText(email)) {
			return new ForgotPwdRes(RtnCode.DATA_ERROR.getCode(), RtnCode.DATA_ERROR.getMsg(), null, null, null);
		}
		if(!adminInfoDao.existsById(id)) {
			return new ForgotPwdRes(RtnCode.ACCOUNT_NOT_FOUND.getCode(), RtnCode.ACCOUNT_NOT_FOUND.getMsg(), null, null, null);
		}
		Optional<AdminInfo> res = adminInfoDao.findById(id);
		AdminInfo targetAdmin = res.get();
		
		if(!targetAdmin.getEmail().equals(email)) {
			return new ForgotPwdRes(RtnCode.VERIFY_ERROR.getCode(), RtnCode.VERIFY_ERROR.getMsg(), null, null, null);
		}
		
		String verifyCode = RandomString.make(6);//之後看要怎麼加上限時3分鐘
		
		String mailContent = "您的驗證碼為：" + verifyCode + "。";
		
		emailService.sendEmail(email, "【選課系統】修改密碼驗證碼", mailContent);
		
		return new ForgotPwdRes(RtnCode.SUCCESSFUL.getCode(), RtnCode.SUCCESSFUL.getMsg(), id, encoder.encode(id), encoder.encode(verifyCode));
	}

	@Override
	public ChangePwdRes forgotNewPwdAdmin(String id, String newPwd) {
		
		if(!StringUtils.hasText(id) || !StringUtils.hasText(newPwd) || newPwd.length() < 8) {
			return new ChangePwdRes(RtnCode.DATA_ERROR.getCode(), RtnCode.DATA_ERROR.getMsg());
		}
		if(!adminInfoDao.existsById(id)) {
			return new ChangePwdRes(RtnCode.ACCOUNT_NOT_FOUND.getCode(), RtnCode.ACCOUNT_NOT_FOUND.getMsg());
		}
		Optional<AdminInfo> res = adminInfoDao.findById(id);
		AdminInfo targetAdmin = res.get();
		
		//新密碼不得跟舊密碼相同
		if(encoder.matches(newPwd, targetAdmin.getPwd())) {
			return new ChangePwdRes(RtnCode.DATA_EXISTED.getCode(), RtnCode.DATA_EXISTED.getMsg());
		}
		
		targetAdmin.setPwd(encoder.encode(newPwd));
		adminInfoDao.save(targetAdmin);
		
		return new ChangePwdRes(RtnCode.SUCCESSFUL.getCode(), RtnCode.SUCCESSFUL.getMsg());
	}

	@Override
	public ForgotPwdRes forgorPwdStudent(String id, String email) {

		if(!StringUtils.hasText(id) || !StringUtils.hasText(email)) {
			return new ForgotPwdRes(RtnCode.DATA_ERROR.getCode(), RtnCode.DATA_ERROR.getMsg(), null, null, null);
		}
		if(!studentInfoDao.existsById(id)) {
			return new ForgotPwdRes(RtnCode.ACCOUNT_NOT_FOUND.getCode(), RtnCode.ACCOUNT_NOT_FOUND.getMsg(), null, null, null);
		}
		
		Optional<StudentInfo> res = studentInfoDao.findById(id);
		StudentInfo targetStudent = res.get();
		
		if(!targetStudent.getEmail().equals(email)) {
			return new ForgotPwdRes(RtnCode.VERIFY_ERROR.getCode(), RtnCode.VERIFY_ERROR.getMsg(), null, null, null);
		}
		
		String verifyCode = RandomString.make(6);//之後看要怎麼加上限時3分鐘
		
		String mailContent = "您的驗證碼為：" + verifyCode;
		
		emailService.sendEmail(email, "【選課系統】修改密碼驗證碼", mailContent);
		
		return new ForgotPwdRes(RtnCode.SUCCESSFUL.getCode(), RtnCode.SUCCESSFUL.getMsg(), id, encoder.encode(id), encoder.encode(verifyCode));

	}

	@Override
	public ChangePwdRes forgotNewPwdStudent(String id, String newPwd) {
		
		if(!StringUtils.hasText(id) || !StringUtils.hasText(newPwd) || newPwd.length() < 8) {
			return new ChangePwdRes(RtnCode.DATA_ERROR.getCode(), RtnCode.DATA_ERROR.getMsg());
		}
		if(!studentInfoDao.existsById(id)) {
			return new ChangePwdRes(RtnCode.ACCOUNT_NOT_FOUND.getCode(), RtnCode.ACCOUNT_NOT_FOUND.getMsg());
		}
		Optional<StudentInfo> res = studentInfoDao.findById(id);
		StudentInfo targetStudent = res.get();
		
		//新密碼不得跟舊密碼相同
		if(encoder.matches(newPwd, targetStudent.getPwd())) {
			return new ChangePwdRes(RtnCode.DATA_EXISTED.getCode(), RtnCode.DATA_EXISTED.getMsg());
		}
		
		targetStudent.setPwd(encoder.encode(newPwd));
		studentInfoDao.save(targetStudent);
		
		return new ChangePwdRes(RtnCode.SUCCESSFUL.getCode(), RtnCode.SUCCESSFUL.getMsg());
	}

	@Override
	public EditInfoRes editInfo(String identity, String id, String name, String email, LocalDateTime birthday) {
		if(!StringUtils.hasText(name) || !StringUtils.hasText(email)) {
			return new EditInfoRes(RtnCode.DATA_ERROR.getCode(), RtnCode.DATA_ERROR.getMsg());
		}
		if(identity.equals("admin")) {
			Optional<AdminInfo> res = adminInfoDao.findById(id);
			if(res.isEmpty()) {
				return new EditInfoRes(RtnCode.ACCOUNT_NOT_FOUND.getCode(), RtnCode.ACCOUNT_NOT_FOUND.getMsg());
			}else {
				AdminInfo target = res.get();
				target.setName(name);
				target.setEmail(email);
				target.setBirthday(birthday);
				adminInfoDao.save(target);
				
				return new EditInfoRes(RtnCode.SUCCESSFUL.getCode(), RtnCode.SUCCESSFUL.getMsg());
			}
		}
		
		if(identity.equals("student")) {
			Optional<StudentInfo> res = studentInfoDao.findById(id);
			if(res.isEmpty()) {
				return new EditInfoRes(RtnCode.ACCOUNT_NOT_FOUND.getCode(), RtnCode.ACCOUNT_NOT_FOUND.getMsg());
			}else {
				StudentInfo target = res.get();
				target.setName(name);
				target.setEmail(email);
				target.setBirthday(birthday);
				studentInfoDao.save(target);
				
				return new EditInfoRes(RtnCode.SUCCESSFUL.getCode(), RtnCode.SUCCESSFUL.getMsg());
			}
		}
		
		return new EditInfoRes(RtnCode.DATA_ERROR.getCode(), RtnCode.DATA_ERROR.getMsg());
	}




}
