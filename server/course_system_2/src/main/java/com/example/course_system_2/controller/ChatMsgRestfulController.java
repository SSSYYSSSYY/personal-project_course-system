package com.example.course_system_2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.course_system_2.entity.CourseChatMsg;
import com.example.course_system_2.repository.CourseChatMsgDao;

@CrossOrigin
@RestController
public class ChatMsgRestfulController {

	@Autowired
	private CourseChatMsgDao courseChatMsgDao;
	
	@GetMapping(value = "/get_msg_by_course_code/{courseCode}")
	public List<CourseChatMsg> findByCourseCode(@PathVariable String courseCode){
		return courseChatMsgDao.findByCourseCode(courseCode);
	}
}
