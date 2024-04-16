package edu.kh.project.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import jakarta.servlet.MultipartConfigElement;

@Configuration
@PropertySource("classpath:/config.properties")
public class FileConfig {
	
	// 파일 디스크에 쓸 때까지의 임계값 
	@Value("${spring.servlet.multipart.file-size-threshold}")
	private long fileSizeThreshold; 
	
	// 요청당 파일 최대 크기 
	@Value("${spring.servlet.multipart.max-request-size}")
	private long maxRequestSize; 
	
	// 개별 파일당 최대크기 
	@Value("${spring.servlet.multipart.max-file-size}")
	private long maxFileSize; 
	
	//임계값 초과시 임시 저장 폴더 경로 
	@Value("${spring.servlet.multipart.location}")
	private String location; 
	
	
	
	/* MultipartResolver 설정 */
	@Bean
	public MultipartConfigElement configElement() {
		
		// MultipartConfigElement 
		// : 파일 업로드를 처리하는데 사용되는 MultipartConfigElement 를 구성하고 반환 
		// 파일 업로드를 위한 구성 옵션을 설정하는데 사용
		// 업로드 파일의 최대 크기, 메모리에서의 임시 저장 경로 등을 설정 가능 
		// 보안상 이 클래스에 직접 작성하지 않고 config.properties 에 작성한 내용을 가져올것
		
		MultipartConfigFactory factory = new MultipartConfigFactory(); 
		
		factory.setFileSizeThreshold(DataSize.ofBytes(fileSizeThreshold));
		
		factory.setMaxFileSize(DataSize.ofBytes(maxFileSize));
		
		factory.setMaxRequestSize(DataSize.ofBytes(maxRequestSize));
		
		factory.setLocation(location);
		
		return factory.createMultipartConfig(); 
		
	}
	
	//MultipartResolver 객체를 Bean으로 추가
	// -> 추가 후 위에서 만든 MultipartConfig 자동으로 이용 
	@Bean
	public MultipartResolver multipartResolver() {
		// MultipartResolver : MultipartFile 을 처리해주는 해결사 
		// MultipartResolver는 클라이언트로부터 받은 멀티파트 요청을 처리하고, 
		// 이 중에서 업로드된 파일을 추출하여 MultipartFile 객체로 제공하는 역할
		
		StandardServletMultipartResolver multipartResolver = new StandardServletMultipartResolver(); 
		
		return multipartResolver; 
		
	}
	
	
	
	
	

}
