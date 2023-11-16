package com.example.course_system_2.service.ifs;

import java.time.LocalDateTime;

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
import com.example.course_system_2.entity.StudentInfo;
import com.example.course_system_2.vo.StudentData;

public interface UserService {
	

	public AddAdminRes addAdmin(AdminInfo adminInfo);
	
	public AddStudentRes addStudent(StudentInfo studentInfo);
	
	public StudentLoginRes studentLogin(String studentId, String pwd);
	
	public StudentDetailRes getStudentDetail(String studentId, String loginToken);
	
	public AdminLoginRes adminLogin(String adminId, String adminPwd);
	
	public LogoutRes logout();
	
	public ChangePwdRes studentChangePwd(String studentId, String oldPwd, String newPwd);
	
	public ChangePwdRes adminChangePwd(String adminId, String oldPwd, String newPwd);
	
	public ForgotPwdRes forgorPwdAdmin(String id, String email);
	
	public ChangePwdRes forgotNewPwdAdmin(String id, String newPwd);
	
	public ForgotPwdRes forgorPwdStudent(String id, String email);
	
	public ChangePwdRes forgotNewPwdStudent(String id, String newPwd);
	
	public EditInfoRes editInfo(String identity, String id, String name, String email, LocalDateTime birthday);
}
