package edu.kh.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

// Java 객체 : new 연산자에 의해 Heap 영역에 
//			   클래스에 작성된 내용대로 생성된 것

// instance : 개발자가 만들고 관리하는 객체

// Bean : Spring Container(Spring)가 만들고 관리하는 객체 

@Controller // 요청, 응답을 제어할 컨트롤러 역할을 명시 + Bean으로 등록(== 객체로 생성해서 스프링이 관리)
public class TestController {
	// 기존 Servlet : 클래스 단위로 하나의 요청만 처리 가능 
	// Spring : 메서드 단위로 요청 처리 가능 
	
	// @RequestMapping("요청주소") 
	// - 요청 주소를 처리할 메서드를 매핑하는 어노테이션 
	
	// 1) 메서드에 작성 
	// - 요청주소와 메서드를 매핑 
	// - GET/POST 가리지 않고 매핑 (속성을 통해 지정 가능) (value="", method=RequestMethod=""):잘 사용하지 않음  
	// @GetMapping @PostMapping 따로 있음 
	
	// 2) 클래스에 작성 
	// - 공통 주소를 매핑 
	// ex) /todo/insert, /todo/select, /todo/update 
	
	/* 
	  
	   @RequestMapping("todo") 
	   @Controller 
	   public class TodoController {
	   
	   		@RequestMapping("insert")      // /todo/insert 매핑 	
	   		public String insert() {}
	   		
	   		@RequestMapping("select")      // /todo/select 매핑 
	   	    public String select() {}	
	   		
	   		@RequestMapping("update")      // /todo/update 매핑 
	   	    public String update() {}	
	   	    
	   	  }   
	 */
	
	//******************************************
	
	// springboot 컨트롤러에서는 매핑주소 앞에 '/' 작성하지 않음(특수한 경우 제외) 
	
	//******************************************
	
	
	@RequestMapping("test") // /test 요청시 처리할 메서드 매핑 (GET/POST 가리지 않고) 
	public String testMethod() {
		
		System.out.println("/test 요청 받음");
		
		/* Controller 메서드의 반환형이 String인 이유 
		 * - 메서드에서 반환되는 문자열이 
		 * forward할 html 파일 경로가 되기 때문 
		 * */
		
		/* Thymeleaf : JSP 대신 사용하는 템플릿 엔진
		 * 
		 * classpath : == src/main/resources
		 * 접두사 : classpath:/templates/ (경로) 
		 * 접미사 : .html (확장자)  
		 * 
		 * */
		
		//src/main/resources/templates/test.html		
		return "test";   //forward(접두사+반환값+접미사 경로의 html 파일로 forward)  
		// return에 경로명 작성 
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
