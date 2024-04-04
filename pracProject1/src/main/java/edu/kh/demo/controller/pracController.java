package edu.kh.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;

public class pracController {
	
	@PostMapping("login")
	public String login(HttpServletRequest req) {
		
		String id = req.getParameter(id); 
		String pw = req.getParameter(pw);
		
		
		return "/"; 
	}
	
	
	
	
	
	
	

}
