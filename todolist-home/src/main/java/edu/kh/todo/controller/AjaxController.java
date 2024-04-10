package edu.kh.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.kh.todo.model.dto.Todo;
import edu.kh.todo.model.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.proxy.annotation.Post;


@Slf4j         // log 사용 가능하게
@RequestMapping("ajax")
@Controller    // 요청, 응답 제어하는 역할 명시 + Bean 객체로 등록
public class AjaxController {

	@Autowired    // 등록된 Bean 중 같은 타입 또는 상속관계인 Bean을 해당 필드에 의존성 주입(DI)
	private TodoService service; 
	

	@GetMapping("main")     //  /ajax/main GET요청 매핑 
	public String ajaxMain() {	
		
		// 접두사 : classpath:templates/
		// 접미사 : .html (확장자) 
		return "ajax/main"; 
	}
	
	
	// 전체 todo 개수 조회 
	@ResponseBody   
	@GetMapping("totalCount")
	public int getTotalCount() {
		
		// 전체 할 일 개수 조회 서비스 호출 및 응답 
		int totalCount = service.getTotalCount(); 
		
		return totalCount; 
		// spring 에서 return forward나 redirect를 해야 하는 자리 
		
	}
	
	
	// 완료한 todo 개수 조회 
	@ResponseBody
	@GetMapping("completeCount")
	public int getCompleteCount() {
		
		return service.getCompleteCount(); 
		
		
	}
	
	
	// 할 일 추가 
	@ResponseBody
	@PostMapping("add")
	public int addTodo(@RequestBody Todo todo) {
		
		// requestBody : 비동기 요청시 전달되는 데이터 중 body 부분에 포함된 데이터를
		// 알맞은 Java 객체 타입으로 바인딩하는 어노테이션 
		// 비동기 요청시 body 에 삼긴 값을 알맞은 타입으로 변환해서 매개변수에 저장 
		
		return service.addTodo(todo.getTodoTitle(), todo.getTodoContent()); 
		
	}
	
	
	// 할 일 목록 조회 
	@ResponseBody
	@GetMapping("selectList")
	public List<Todo> selectList() {
		
		List<Todo> todoList = service.selectList(); 
		return todoList; 
	}
	
	
	
	//할 일 상세 조회 
	@ResponseBody
	@GetMapping("detail")
	public Todo selectTodo(@RequestParam("todoNo") int todoNo) {
		
		return service.todoDetail(todoNo); 
		
	}
	
	
	// 할 일 삭제하기 
	@ResponseBody
	@DeleteMapping("delete")  // deleteMapping : 비동기 요청만 가능 
	public int todoDelete(@RequestBody int todoNo) {
		
		return service.todoDelete(todoNo); 
		
	}
	
	
	// 완료 여부 변경 
	@ResponseBody
	@PutMapping("changeComplete")
	public int changeComplete(@RequestBody Todo todo) {
		
		return service.changeComplete(todo); 
		
	}
 	
	
	//  할 일 수정 
	@ResponseBody
	@PutMapping("update")
	public int todoUpdate(@RequestBody Todo todo) {
		return service.todoUpdate(todo); 
	}
	
	
	
	
	
	
	
	
	
	
	
}
