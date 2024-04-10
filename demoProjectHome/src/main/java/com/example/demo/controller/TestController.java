package com.example.demo.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("param")
public class TestController {
	
	@PostMapping("test3")
	public String paramTest3(
			@RequestParam(value="color", required=false) String[] colorArr,
			@RequestParam(value="fruit", required=false) List<String> fruitList,
			@RequestParam Map<String, Object> paramMap  ) {
		
		
		
		return "index"; 
		
	}
	
	
	
	
	

}
