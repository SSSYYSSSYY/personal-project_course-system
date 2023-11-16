package com.example.course_system_2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.example.course_system_2.entity.CourseChatMsg;
import com.example.course_system_2.repository.CourseChatMsgDao;

@CrossOrigin
@Controller
public class ChatController {

	@Autowired
	private CourseChatMsgDao courseChatMsgDao;
	
	@MessageMapping("/chat/{courseCode}")//要send到「/app/chat/{動態id}」
	@SendTo("/topic/chat/{courseCode}")//要訂閱「/topic/chat/{動態id}」
	public CourseChatMsg sendMsg(@DestinationVariable("courseCode") String courseCode, CourseChatMsg courseChatMsg) {
		
		CourseChatMsg m = courseChatMsgDao.save(courseChatMsg);
		
		return m;
	}
}
