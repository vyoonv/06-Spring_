package edu.kh.project.myPage.model.service;

import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.project.member.model.dto.Member;
import edu.kh.project.myPage.model.mapper.MyPageMapper;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(rollbackFor=Exception.class)   // 모든 예외 발생시 롤백 (없으면 runtime exception)
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService {

	private final MyPageMapper mapper;
	
	// BCrypt 암호화 객체 의존성 주입 (SecurityConfig 참고) 
	private final BCryptPasswordEncoder bcrypt; 

	/**
	 * 회원 정보 수정 
	 */
	@Override
	public int updateInfo(Member inputMember, String[] memberAddress) {
		
		// 입력된 주소가 있을 경우 
		// memberAddress를 A^^^B^^^C 형태로 가공 
		
		// 주소 입력 안한 경우 -> inputMember.getAddress() -> ",,"
		if(inputMember.getMemberAddress().equals(",,")) {
			
			// 주소에 null 대입 
			inputMember.setMemberAddress(null);
			
		} else { 
			
			// 주소를 A^^^B^^^C 형태로 가공 
			String address = String.join("^^^", memberAddress);
			
			// 주소에 가공된 데이터를 대입 
			inputMember.setMemberAddress(address);
			
		}
		
		// SQL 수행 후 결과 반환 
		return mapper.updateInfo(inputMember);
	}

	/**
	 * 비밀번호 수정 
	 */
	@Override
	public int changePw(Map<String, Object> paramMap, int memberNo) {
		
		// 현재 로그인한 회원의 암호화된 비밀번호를 DB에서 조회 
		String originPw = mapper.selectPw(memberNo); 
		
		// 현재 입력받은 비번(평문상태) 
		// DB에서 조회한 비밀번호 비교(암호화) 
		// BCryptPasswordEncoder.matches(평문, 암호화비밀번호) 
		
		// 다를 경우 
		if( !bcrypt.matches((String)paramMap.get("currentPw"), originPw)) {
			return 0; 
		}
		// 같을 경우 
		// 새 비밀번호를 암호화 진행 
		String encPw = bcrypt.encode( (String)paramMap.get("newPw") );
		
		// 암호화된 비번 paramMap에 전달 
		paramMap.put("encPw", encPw); 
		paramMap.put("memberNo", memberNo); 
		
		return mapper.changePw(paramMap);
	}

	
	/**
	 * 회원 탈퇴 
	 */
	@Override
	public int secession(String memberPw, int memberNo) {
		
		// 현재 로그인한 회원의 암호화된 비밀번호를 DB에서 조회 
		String originPw = mapper.selectPw(memberNo);
		
		// 다를 경우 
		if( !bcrypt.matches(memberPw, originPw)) {
			return 0; 
		}
		
		// 같을 경우 
		return mapper.secession(memberNo);
	} 
	
	
	
}
