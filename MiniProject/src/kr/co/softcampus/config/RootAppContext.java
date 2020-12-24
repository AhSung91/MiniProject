package kr.co.softcampus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//데이터 저장및 관리목적으로 사용하는 Bean
//프로젝트 작업시 사용할 bean을 정의하는 클래스
import org.springframework.web.context.annotation.SessionScope;

import kr.co.softcampus.beans.UserBean;
@Configuration
public class RootAppContext {
	
	//loginUserBean이라는 이름으로 UserBean을 담아서session에 등록한다
	@Bean("loginUserBean")
	@SessionScope
	public UserBean loginUserBean() {
		return new UserBean();
	}
}
