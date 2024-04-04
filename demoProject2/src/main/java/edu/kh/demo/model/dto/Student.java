package edu.kh.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


//Spring EL 같은 경우 getter가 필수로 작성되어있어야 함
// -> ${Student.getName() == @{student.name}
//getter 대신 필드명 호출하는 형식으로 작성하는데 
// 자동으로 getter 호출하기 때문 

@AllArgsConstructor // 모든 필드를 초기화하는 용도의 매개변수 생성자 
@NoArgsConstructor  // 매개변수 없는 생성자(==기본 생성자)
@ToString
@Setter
@Getter
public class Student {
	
	
	private String studentNo; 
	private String name; 
	private int age; 
	
	

	

}
