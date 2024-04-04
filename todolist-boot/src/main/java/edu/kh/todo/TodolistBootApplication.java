package edu.kh.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TodolistBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodolistBootApplication.class, args);
	}

	
	// Connection : DB 연결 정보 담고있는 객체 JDBC 수행 가능 
	// Pool : 모여있는 곳 
	// Connection pool : Connection이 모여있는 곳
	// servlet -> service(getConnection == 커넥션 생성) -> dao 호출 
	// 수행 끝나면 앞 단으로 되돌아 오는 것 
	// HikariCP(Hikari Connection Pool) 
	// Server 실행시 DB와 연결된 Connection을 일정 개수 생성 
	// 클라이언트 요청시 만들어진 Connection 대여 -> 요청이 처리 완료되면 다시 Connection 반납 받음 
	// 생성/소멸(반환) 걸리는 시간이 현격히 감소 
	// 대기열 + DB 연결 과부하 예방 
	
	
	
	
}
