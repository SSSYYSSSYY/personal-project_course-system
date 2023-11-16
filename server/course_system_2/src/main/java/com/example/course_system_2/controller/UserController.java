package com.example.course_system_2.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.course_system_2.constants.RtnCode;
import com.example.course_system_2.dto.AddAdminRes;
import com.example.course_system_2.dto.AddCourseReq;
import com.example.course_system_2.dto.AddStudentRes;
import com.example.course_system_2.dto.AdminInfoRes;
import com.example.course_system_2.dto.AdminLoginReq;
import com.example.course_system_2.dto.AdminLoginRes;
import com.example.course_system_2.dto.ChangePwdReq;
import com.example.course_system_2.dto.ChangePwdRes;
import com.example.course_system_2.dto.CheckLoginTokenReq;
import com.example.course_system_2.dto.CheckLoginTokenRes;
import com.example.course_system_2.dto.CheckVerifyCodeReq;
import com.example.course_system_2.dto.CheckVerifyCodeRes;
import com.example.course_system_2.dto.EditInfoReq;
import com.example.course_system_2.dto.EditInfoRes;
import com.example.course_system_2.dto.ForgotPwdNewReq;
import com.example.course_system_2.dto.ForgotPwdReq;
import com.example.course_system_2.dto.ForgotPwdRes;
import com.example.course_system_2.dto.GetInfoAfterLoginReq;
import com.example.course_system_2.dto.StudentDetailRes;
import com.example.course_system_2.dto.StudentLoginReq;
import com.example.course_system_2.dto.StudentLoginRes;
import com.example.course_system_2.entity.AdminInfo;
import com.example.course_system_2.entity.StudentInfo;
import com.example.course_system_2.entity.StudentProgress;
import com.example.course_system_2.repository.AdminInfoDao;
import com.example.course_system_2.repository.StudentInfoDao;
import com.example.course_system_2.repository.StudentProgressDao;
import com.example.course_system_2.service.ifs.UserService;

@CrossOrigin
@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AdminInfoDao adminInfoDao;
	
	@Autowired
	private StudentInfoDao studentInfoDao;
	
	@Autowired
	private StudentProgressDao studentProgressDao;
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@PostMapping(value = "/add_admin")
	public AddAdminRes addAdmin(@RequestBody AdminInfo adminInfo) {
		return userService.addAdmin(adminInfo);
	}
	
	@PostMapping(value = "/add_student")
	public AddStudentRes addStudent(@RequestBody StudentInfo studentInfo) {
		return userService.addStudent(studentInfo);
	}
	
	@PostMapping(value = "/login/student")
	public StudentLoginRes studentLogin(@RequestBody StudentLoginReq studentLoginReq) {
		StudentLoginRes res = userService.studentLogin(studentLoginReq.getStudentId(), studentLoginReq.getPwd());
		return res;
	}
	
	@PostMapping(value = "/student_detail")
	public StudentDetailRes getStudentDetail(@RequestBody GetInfoAfterLoginReq req) {
		if(!encoder.matches(req.getId(), req.getLoginToken())) {
			return new StudentDetailRes(RtnCode.VERIFY_ERROR.getCode(), RtnCode.VERIFY_ERROR.getMsg(), null);
		}
		return userService.getStudentDetail(req.getId(), req.getLoginToken());
	}
	
	@PostMapping(value = "/login/admin")
	public AdminLoginRes adminLogin(@RequestBody AdminLoginReq adminLoginReq) {
		AdminLoginRes res = userService.adminLogin(adminLoginReq.getAdminId(), adminLoginReq.getPwd());
		if(res.getPermission() == null) {
			res.setPermission(99);
		}
		return res;
	}
	
	@PostMapping(value = "/admin_info_after_login/")
	public AdminInfoRes getAdminInfoAfterLogin(@RequestBody GetInfoAfterLoginReq req) {
		if(!encoder.matches(req.getId(), req.getLoginToken())) {
			return new AdminInfoRes(RtnCode.VERIFY_ERROR.getCode(), RtnCode.VERIFY_ERROR.getMsg(), null);
		}
		Optional<AdminInfo> res = adminInfoDao.findById(req.getId());
		if(res.isEmpty()) {
			return new AdminInfoRes(RtnCode.ACCOUNT_NOT_FOUND.getCode(), RtnCode.ACCOUNT_NOT_FOUND.getMsg(), null);
		}
		return new AdminInfoRes(RtnCode.SUCCESSFUL.getCode(), RtnCode.SUCCESSFUL.getMsg(), res.get());

	}
	
	//檢查客戶端的token是否正確
	@PostMapping(value = "/check_login_token")
	public CheckLoginTokenRes checkLoginToken(@RequestBody CheckLoginTokenReq req) {
		if(encoder.matches(req.getId(), req.getLoginToken())) {
			return new CheckLoginTokenRes(RtnCode.SUCCESSFUL.getCode(), RtnCode.SUCCESSFUL.getMsg(), true);
		}else {
			return new CheckLoginTokenRes(RtnCode.VERIFY_FAILED.getCode(), RtnCode.VERIFY_FAILED.getMsg(), false);
		}
	}
	
	//加上管理員/學生修改個人資料的API
	
	@PostMapping(value = "/student/change_pwd")
	public ChangePwdRes studentChangePwd(@RequestBody ChangePwdReq changePwdReq) {
		return userService.studentChangePwd(changePwdReq.getId(), changePwdReq.getOldPwd(), changePwdReq.getNewPwd());
	}
	
	@PostMapping(value = "/admin/change_pwd")
	public ChangePwdRes adminChangePwd(@RequestBody ChangePwdReq changePwdReq) {
		return userService.adminChangePwd(changePwdReq.getId(), changePwdReq.getOldPwd(), changePwdReq.getNewPwd());
	}
	
	//忘記密碼(管理員)
	@PostMapping(value = "/forgot_pwd_admin")
	public ForgotPwdRes forgotPwdAdmin(@RequestBody ForgotPwdReq req) {
		System.out.println(req.toString());
		return userService.forgorPwdAdmin(req.getId(), req.getEmail());
	}
	
	//忘記密碼(學生)
	@PostMapping(value = "/forgot_pwd_student")
	public ForgotPwdRes forgotPwdStudent(@RequestBody ForgotPwdReq req) {
		System.out.println(req.toString());
		return userService.forgorPwdStudent(req.getId(), req.getEmail());
	}
	
	@PostMapping(value = "/change_pwd_after_verify_admin")
	public ChangePwdRes ForgotNewPwdAdmin(@RequestBody ForgotPwdNewReq req) {
		System.out.println(req.toString());
		if(!encoder.matches(req.getId(), req.getLoginToken())) {
			return new ChangePwdRes(RtnCode.VERIFY_ERROR.getCode(), RtnCode.VERIFY_ERROR.getMsg());
		}
		return userService.forgotNewPwdAdmin(req.getId(), req.getNewPwd());
	}
	
	@PostMapping(value = "/change_pwd_after_verify_student")
	public ChangePwdRes ForgotNewPwdStudent(@RequestBody ForgotPwdNewReq req) {
		System.out.println(req.toString());
		if(!encoder.matches(req.getId(), req.getLoginToken())) {
			return new ChangePwdRes(RtnCode.VERIFY_ERROR.getCode(), RtnCode.VERIFY_ERROR.getMsg());
		}
		return userService.forgotNewPwdStudent(req.getId(), req.getNewPwd());
	}
	
	
	@PostMapping(value = "/check_verify_code")
	public CheckVerifyCodeRes checkVerifyCode(@RequestBody CheckVerifyCodeReq req) {
		if(!encoder.matches(req.getId(), req.getLoginToken())) {
			//400
			return new CheckVerifyCodeRes(RtnCode.VERIFY_ERROR.getCode(), RtnCode.VERIFY_ERROR.getMsg());
		}
		if(!encoder.matches(req.getInputVerifyCode(), req.getEncodedVerifyCode())) {
			//403
			return new CheckVerifyCodeRes(RtnCode.VERIFY_FAILED.getCode(), RtnCode.VERIFY_FAILED.getMsg());
		}
		//200
		return new CheckVerifyCodeRes(RtnCode.SUCCESSFUL.getCode(), RtnCode.SUCCESSFUL.getMsg());
	}
	
	@GetMapping(value = "/get_all_admin")
	public List<AdminInfo> getAllAdmin(){
		return adminInfoDao.findAll();
	}
	
	@GetMapping(value = "/get_all_student")
	public List<StudentInfo> getAllStudent(){
		return studentInfoDao.findAll();
	}
	
	@GetMapping(value = "/get_progress_list/{studentId}")
	public List<StudentProgress> getProgressList(@PathVariable String studentId){
		return studentProgressDao.findByStudentId(studentId);
	}
	
	@GetMapping(value = "/get_all_progress")
	public List<StudentProgress> getAllProgress(){
		return studentProgressDao.findAll();
	}
	
	@PatchMapping(value = "/edit_info")
	public EditInfoRes editInfo(@RequestBody EditInfoReq req) {
		return userService.editInfo(req.getIdentity(), req.getId(), req.getName(), req.getEmail(), req.getBirthday());
	}
	
}
