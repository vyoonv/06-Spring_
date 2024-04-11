package edu.kh.project.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.project.member.model.dto.Member;
import edu.kh.project.member.model.mapper.MemberMapper;
import lombok.extern.slf4j.Slf4j;

									   // 예외(RuntimeException)가 발생하지 않으면 commit
									   // 예외(RuntimeException)가 발생하면 rollback	
@Transactional(rollbackFor=Exception.class)  // 해당 클래스 메서드 종료시까지 
@Service  // 비즈니스 로직 처리 역할 명시 + Bean 등록 
@Slf4j
public class MemberServiceImpl implements MemberService {

	// 등록된 bean 중에서 같은 타입 또는 상속관계인 bean을 자동으로 의존성 주입 (DI)
	@Autowired
	private MemberMapper mapper;

	//BCrypt 암호화 객체 의존성 주입 (SecurityConfig 참고)
	@Autowired
	private BCryptPasswordEncoder bcrypt; 
	
	
	
	/**
	 * 로그인 서비스 
	 */
	@Override
	public Member login(Member inputMember) {
		// memberEmail // memberPw 
		// 입력한 밸류값이 inputMember에 넘어옴 
		
		// 테스트 
		// bcrypt.encode(문자열) : 문자열을 암호화하여 반환 
		//String bcryptPassword = bcrypt.encode(inputMember.getMemberPw());
		//log.debug("bcryptPassword : " + bcryptPassword); 
		
		//boolean result = bcrypt.matches(inputMember.getMemberPw(), bcryptPassword); 
		//log.debug("result : " + result);
		
		// 1. 이메일이 일치하면서 탈퇴하지 않은 회원 조회 
		Member loginMember = mapper.login(inputMember.getMemberEmail()); 
		
		// 2. 만약 일치하는 이메일이 없어 조회 결과가 null인 경우 
		if(loginMember == null) return null; 
		
		// 3. 일치하는 회원이 있으면 입력 받은 비밀번호와 암호화된 비밀번호가 일치하는지 여부 확인
		// (inputMember.getMemberPw()평문 // loginMember.getMemberPw() 암호화된 비밀번호)
		
		// 일치하지 않으면 앞에 !붙여서 return 값 true되게 처리한 후 
		if( !bcrypt.matches(inputMember.getMemberPw(), loginMember.getMemberPw())) {
			return null; 
		} 
		
		// 로그인 결과에서 비밀번호 제거 
		loginMember.setMemberPw(null); 
		
		return loginMember;
	}


	
	/** 
	 * 이메일 중복 검사 
	 */
	@Override
	public int checkEmail(String memberEmail) {
				
		return mapper.checkEmail(memberEmail);
	} 
	
	
	
	
	
	
	
	
	
	
}


/* BCrypt 암호화 (Spring Security 제공) 
 *  - 입력된 문자열(비밀번호)에 salt를 추가한 후 암호화 
 *  ex) A회원 : 1234 -> $12!asdfg
 *  ex) B회원 : 1234 -> $12!dfgsf
 *  
 *  - 비밀번호 확인 방법 
 *  -> BCryptPasswordEncoder.matches(평문 비밀번호, 암호화된 비밀번호)
 *   -> 평문 비밀번호와 암호화된 비밀번호가 같은 경우 true, 아니면 false 반환 
 *   
 * * 로그인 / 비밀번호 변경 / 탈퇴 등 비밀번호가 입력되는 경우 
 *   DB에 저장된 암호화된 비밀번호를 조회해서 matches() 메서드로 비교해야 함
 *   
 *   
 *   
 *  
 *  
 *  
 *  (참고/ 보안 정도가 약해서 사용할 일 x) 
 *  sha 방식 암호화 
 *  ex) A회원 : 1234 -> 암호화 : abcd 
 *      B회원 : 1234 -> 암호화 : abcd (암호화시 변경된 내용이 같음) 
 * */