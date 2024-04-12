package edu.kh.project.member.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.project.member.model.dto.Member;


@Mapper
public interface MemberMapper {

	/**
	 * 로그인 SQL 실행
	 * @param memberEmail
	 * @return loginMember
	 */
	Member login(String memberEmail);

	/** 이메일 중복검사 
	 * @param memberEmail
	 * @return count 
	 */
	int checkEmail(String memberEmail);

	/** 닉네임 중복 검사 
	 * @param memberNickname
	 * @return count 
	 */
	int checkNickname(String memberNickname);

	/** 회원 가입 
	 * @param inputMember
	 * @return result 
	 */
	int signup(Member inputMember);

	
	
	
	
	
	
	
	
	
	
	
	
	
}
