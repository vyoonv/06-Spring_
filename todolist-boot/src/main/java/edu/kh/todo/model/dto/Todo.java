package edu.kh.todo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
public class Todo {

	private int todoNo;  // 번호
	private String todoTitle;  // 제목
	private String todoContent;  // 내용
	private String complete; // 완료 여부
	private String regDate;  // 등록일 (Sting으로 변환) 
	
	
	
}
