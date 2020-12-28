package kr.co.softcampus.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import kr.co.softcampus.beans.UserBean;

	//로그인 확인 여부를 위한 Interceptor
public class CheckLoginInterceptor implements HandlerInterceptor{
	
	private UserBean loginUserBean;
	
	public CheckLoginInterceptor(UserBean loginUserBean) {
		this.loginUserBean = loginUserBean;
	}
	@Override
		public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
				throws Exception {
			// TODO Auto-generated method stub
			if (loginUserBean.isUserLogin() == false) {
				String contextPath = request.getContextPath();
				response.sendRedirect(contextPath + "/user/not_login");	//로그인에 실패하였을경우 해당 jsp로 보낸다
				return false;	//preHandle은 false값을 반환하면 해당 interceptor는 동작하지 않고 끝이난다
			}
			return true;	//다음단계로 진행(controller로 진행)
		}
	
}
