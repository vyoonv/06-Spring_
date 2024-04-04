package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.model.dto.Student;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class StudentController {
	
	@PostMapping("index")
	public String selectStudent(HttpServletRequest req, @ModelAttribute Student student) {

		req.setAttribute("stdName", student.getStdName());

		req.setAttribute("stdAge", student.getStdAge());

		req.setAttribute("stdAddress", student.getStdAddress());

		return "/student/select";

		}

}
