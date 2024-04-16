package edu.kh.project.member.model.service;

import java.util.List;

import edu.kh.project.member.model.dto.Member;

public interface MemberService {

	/** 로그인 서비스 
	 * @param inputMember
	 * @return loginMember
	 */
	Member login(Member inputMember);

	/** 이메일 중복 검사 서비스 
	 * @param memberEmail
	 * @return 0 / 1
	 */
	int checkEmail(String memberEmail);

	/** 닉네임 중복 검사 서비스 
	 * @param memberNickname
	 * @return 0 / 1
	 */
	int checkNickname(String memberNickname);

	/** 회원 가입 서비스 
	 * @param inputMember
	 * @param memberAddress
	 * @return result 
	 */
	int signup(Member inputMember, String[] memberAddress);

	/** 빠른 로그인 
	 * @param memberEmail
	 * @return loginMember
	 */
	Member quickLogin(String memberEmail);

	/** 회원 목록 조회 
	 * @return
	 */
	List<Member> selectMemberList();

	/** 특정 회원 비밀번호 초기화
	 * @param inputNo
	 * @return
	 */
	int resetPw(int inputNo);





	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
