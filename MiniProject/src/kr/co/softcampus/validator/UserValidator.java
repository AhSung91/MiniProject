package kr.co.softcampus.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import kr.co.softcampus.beans.UserBean;

public class UserValidator implements Validator {
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub

		return UserBean.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		UserBean userBean = (UserBean) target;

		//같은UserBean을 사용하기 때문에 겹칠수가 있다.
		//구분을 해주기위해 UserController에서 부여한 이름으로 구분짖기 위해서 생성
		String beanName = errors.getObjectName();

		if (beanName.equals("joinUserBean") || beanName.equals("modifyUserBean")) {	//bean이름이 joinUserBean(회원중복체크)과 modifyUserBean(정보수정)일 때만 실행
			// pw와 pw2가 맞는지 확인
			if (userBean.getUser_pw().equals(userBean.getUser_pw2()) == false) {
				errors.rejectValue("user_pw2", "NotEquals");
			}
		}
			
		if (beanName.equals("joinUserBean")) {
			
			// 중복확인을 하였는지 확인
			if (userBean.isUserIdExist() == false) {
				errors.rejectValue("user_id", "DonCheckUserIdExist");
			}
		}
	}
}
