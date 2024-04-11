package edu.kh.project.email.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.project.email.model.mapper.EmailMapper;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Transactional  // 예외 발생하면 롤백(기본값 커밋) 
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{

	
	// EmailConfig 설정이 적용된 객체(메일 보내기 기능) 
	private final JavaMailSender mailSender;
	
	// Mapper 의존성 주입 
	private final EmailMapper mapper;

	
	
	/** 메일 보내기 
	 * 
	 */
	@Override
	public String sendEmail(String htmlName, String email) {
		
		// 6자리 난수 (인증 코드) 생성 (메서드 호출) 
		String authKey = createAuthKey(); 
		
		try {
			
			// 제목 
			String subject = null; 
			
			switch(htmlName) {
			
			case "signup" : subject = "[boardProject] 회원 가입 인증번호 입니다"; break; 
				
			}
			// 인증 메일 보내기 
			// MimeMessage : Java에서 메일을 보내는 객체 
			MimeMessage mimeMessage = mailSender.createMimeMessage();

			// MimeMessageHelper : 
			// Spring에서 제공하는 메일 발송 도우미 (간단+타임리프) 
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			
			// 1번 매개변수 : mimeMessage 객체 
			// 2번 매개변수 : 파일 전송 사용? true/false
			// 3번 매개변수 : 문자 인코딩 지정
			
			helper.setTo(email); // 받는 사람 이메일 지정 
			helper.setSubject(subject); // 이메일 제목 지정 
			
			helper.setText(authKey);   // 변경예정 -> html로 
			
			// CID(Content-ID)를 이용해 메일에 이미지 첨부 
			// logo 추가 예정 
			
			// 메일 보내기 
			mailSender.send(mimeMessage);
			
			
		} catch(Exception e) {
			e.printStackTrace();
			return null; 
		}
		
			
		return null;
	}
	
	/** 인증번호 생성 (영어 대문자 + 소문자 + 숫자 6자리)
    * @return authKey
    */
   public String createAuthKey() {
	   
	   String key = "";
       for(int i=0 ; i< 6 ; i++) {
          
           int sel1 = (int)(Math.random() * 3); // 0:숫자 / 1,2:영어
          
           if(sel1 == 0) {
              
               int num = (int)(Math.random() * 10); // 0~9
               key += num;
              
           }else {
              
               char ch = (char)(Math.random() * 26 + 65); // A~Z
              
               int sel2 = (int)(Math.random() * 2); // 0:소문자 / 1:대문자
              
               if(sel2 == 0) {
                   ch = (char)(ch + ('a' - 'A')); // 대문자로 변경
               }
              
               key += ch;
           }
	          
	      }
	       return key;
	   }

	
	
	
}



/* Google SMTP를 이용한 이메일 전송하기 
 * 
 * - SMTP(Simple Mail Transfer Protocol, 간단 메일 전송 규약)
 * --> 이메일 메세지를 보내고 받을 때 사용하는 약속(규약, 방법) 
 * 
 * - Google SMTP
 * 
 * Java Mail Sender -> Google SMTP -> 대상에 이메일 전송
 * 
 * - Java Mail Sender에 Google SMTP 이용 설정 추가 
 * 1) config.properties 내용 추가(계정, 앱 비밀번호) 
 * 2) EmailConfig.java  
 * 
 * 
 * */




