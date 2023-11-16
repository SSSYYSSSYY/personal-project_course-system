package com.example.course_system_2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.course_system_2.entity.AdminInfo;

public interface AdminInfoDao extends JpaRepository<AdminInfo, String>{

}
