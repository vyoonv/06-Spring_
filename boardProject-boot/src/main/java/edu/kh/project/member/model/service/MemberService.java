package edu.kh.project.member.model.service;

import edu.kh.project.member.model.dto.Member;

public interface MemberService {

	/** 로그인 서비스 
	 * @param inputMember
	 * @return loginMember
	 */
	Member login(Member inputMember);

	/** 이메일 중복 검사 서비스 
	 * @param memberEmail
	 * @return
	 */
	int checkEmail(String memberEmail);

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
