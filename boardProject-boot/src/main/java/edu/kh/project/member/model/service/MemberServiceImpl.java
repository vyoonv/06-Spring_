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



	/**
	 * 닉네임 중복 검사 
	 */
	@Override
	public int checkNickname(String memberNickname) {
		
		return mapper.checkNickname(memberNickname);
	}



	/**
	 * 회원 가입 
	 */
	@Override
	public int signup(Member inputMember, String[] memberAddress) {
		
		// 주소가 입력되지 않았으면 
		// inputMember.getMemberAddress() -> ",,"
		// memberAddress -> [,,] 
		// 주소 중에 , 포함되는 주소가 있어 주소 출력때 문제 발생 
		// 구분자를 , 대신 다른 기호로 세팅 
		
		
		// 주소가 입력된 경우 
		if( !inputMember.getMemberAddress().equals(",,") ) {
			
			// String.join("구분자", 배열) 
			// -> 배열의 모든 요소 사이에 "구분자"를 추가하여 
			// 하나의 문자열로 만들어 반환하는 메서드 
			String address = String.join("^^^", memberAddress);
			// 구분자로 "^^^" 쓴 이유 : 
			// 주소, 상세주소에 없는 특수문자로 지정
			// 나중에 다시 3분할 할 때 구분자로 이용할 예정 
						
			
			// inputMember 주소로 합쳐진 주소를 세팅 
			inputMember.setMemberAddress(address);
			
		} else { // 주소 입력 안 된 경우 
			
			inputMember.setMemberAddress(null);   // null로 저장 
			
		}
		
		// 이메일, 비밀번호, 닉네임, 전화번호, 주소 
		// 비밀번호 암호화 작업 해줘야 함! -> inputMember에 세팅 
		String encPw = bcrypt.encode(inputMember.getMemberPw());
		inputMember.setMemberPw(encPw);
		
		
		// 회원 가입 매퍼 메서드 호출 
		return mapper.signup(inputMember);
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