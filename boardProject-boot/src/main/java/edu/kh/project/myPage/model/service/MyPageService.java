package edu.kh.project.myPage.model.service;

import java.util.Map;

import edu.kh.project.member.model.dto.Member;

public interface MyPageService {

	/** 회원 정보 수정 
	 * @param inputMember
	 * @param memberAddress
	 * @return result 
	 */
	int updateInfo(Member inputMember, String[] memberAddress);

	/** 비밀번호 수정 
	 * @param paramMap
	 * @param memberNo
	 * @return result 
	 */
	int changePw(Map<String, Object> paramMap, int memberNo);

	/** 회원 탈퇴 
	 * @param memberPw
	 * @param memberNo
	 * @return result 
	 */
	int secession(String memberPw, int memberNo);

	
	
	
	
}
