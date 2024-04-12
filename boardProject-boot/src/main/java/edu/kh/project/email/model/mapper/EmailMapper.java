package edu.kh.project.email.model.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface EmailMapper {

	/** 인증키 db 존재하는 경우 update
	 * @param map
	 * @return
	 */
	int updateAuthKey(Map<String, String> map);

	/** 기존 인증키 없는 경우 insert
	 * @param map
	 * @return
	 */
	int insertAuthKey(Map<String, String> map);

	/** 이메일, 인증번호 확인 
	 * @param map
	 * @return 
	 */
	int checkAuthKey(Map<String, Object> map);
	
	
	
	

}
