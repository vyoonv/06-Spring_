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


/* @ResponseBody
 * - 컨트롤러 메서드의 반환값을 
 *   HTTP 응답 본문에 직접 바인딩하는 역할임을 명시 
 * 
 * - 컨트롤러 메서드의 반환값을 비동기 요청했던 
 *   HTML/JS 파일 부분에 값을 돌려보낼 것을 명시 
 * 
 * - forward/redirect로 인식 X 
 * 
 * 
 * @RequestBody 
 * - 비동기 요청(ajax)시 전달되는 데이터 중 
 *   body 부분에 포함된 요청 데이터를 
 *   알맞은 Java 객체 타입으로 바인딩하는 어노테이션
 *   
 * - 비동기 요청시 body에 담긴 값을 
 * 	 알맞은 타입으로 변환해서 매개변수에 저장 
 * 
 * [HttpMessageConverter]
 * Spring에서 비동기 통신시 
 * - 전달되는 데이터의 자료형
 * - 응답하는 데이터의 자료형
 * 위 두가지를 알맞은 형태로 가공(변환)해주는 객체 
 * 
 * - 문자열, 숫자 <-> TEXT 
 * - DTO <-> JSON
 * - Map <-> JSON
 * 
 * (참고) 
 * HttpMessageConverter가 동작하기 위해서는 
 * Jackson-data-bind 라이브러리가 필요한데 
 * Spring Boot 모듈에 내장되어 있음
 * (Jackson : 자바에서 JSON을 다루는 방법을 제공하는 라이브러리) 
 * 
 *    
 * */



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
	
	
	// 전체 Todo 개수 조회
	// return 은 원래 forward / redirect 	
	@ResponseBody     // 값 그대로 호출한 곳에 돌려 보내는 어노테이션 
	@GetMapping("totalCount")
	public int getTotalCount() {
		
		// 전체 할 일 개수 조회 서비스 호춫 및 응답 
		int totalCount = service.getTotalCount();
		
		return totalCount; 
		
		
	}
	
	// 완료된 Todo 개수 조회 
	@ResponseBody
	@GetMapping("completeCount")
	public int getCompleteCount() {
		
		return service.getCompleteCount(); 
		
	}
	
	
	// Todo 추가  
	@ResponseBody   // 비동기 요청 결과로 값 자체를 반환 
	@PostMapping("add")
	public int addTodo(
					// JSON이 파라미터로 전달된 경우 
					// @RequestParam() 으로 얻어오기 불가능 
			 		
					@RequestBody Todo todo // 요청 body에 담긴 값을 Todo에 저장
					
						) {
							
		log.debug(todo.toString());
		
		return service.addTodo(todo.getTodoTitle(), todo.getTodoContent()); 
	}
	
	
	// 목록 조회 
	@ResponseBody
	@GetMapping("selectList")
	public List<Todo> selectList() {
			
		List<Todo> todoList = service.selectList();
		
		return todoList; 
		
		// List(Java 전용타입)를 반환
		// -> JS가 인식할 수 없기 때문에 
		// HttpMessageConverter가 
		// JSON 형태로 변환하여 반환 
		// -> [{}, {}, {}] JSONArray
		
		
	}
	
	// 상세 조회 
	@ResponseBody
	@GetMapping("detail")
	public Todo selectTodo(@RequestParam("todoNo") int todoNo) {
		
		// return 자료형 : Todo 
		// -> HttpMessageConverter가 String(JSON) 형태로 변환해서 반환 
		return service.todoDetail(todoNo); 
		
	}
	
	// 할 일 삭제 
	@ResponseBody
	@DeleteMapping("delete")   // delete 방식 요청 처리 (비동기 요청만 가능) (delete/put 비동기만 가능) 
	public int todoDelete(@RequestBody int todoNo) {
		
		return service.todoDelete(todoNo); 
		
		
	}
	
	
	// 완료 여부 변경 
	@ResponseBody
	@PutMapping("changeComplete")
	public int changeComplete(@RequestBody Todo todo) {
		
		return service.changeComplete(todo); 
	}
	
	
	// 할 일 수정
	@ResponseBody
	@PutMapping("update")
	public int todoUpdate(@RequestBody Todo todo) {
		
		return service.todoUpdate(todo); 
		
	}
	
	
	
	
	
	
}
