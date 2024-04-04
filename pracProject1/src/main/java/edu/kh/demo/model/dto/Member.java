package edu.kh.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Member {

	private String id; 
	private String pw;
	private String nickname; 
	private String email;
	private int memNo;
	
	
	
}
