package kr.co.softcampus.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import kr.co.softcampus.beans.ContentBean;
import kr.co.softcampus.beans.UserBean;
import kr.co.softcampus.service.BoardService;
//누군가 주소를 직접 치고 올수 있기 때문에 정보수정 및 삭제의 권한을 확인하기위한 interceptor
//interceptor는 bean의 주입이 안되기 때문에 생성자를 통한 데이터를 받아야한다
public class CheckWriterInterceptor implements HandlerInterceptor{
	
	private UserBean loginUserBean;
	private BoardService boardService;
	
	public CheckWriterInterceptor(UserBean loginUserBean, BoardService boardService) {
		this.boardService = boardService;
		this.loginUserBean = loginUserBean;
	}
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		String str1 = request.getParameter("content_idx");
		int content_idx = Integer.parseInt(str1);
		
		ContentBean currentContentBean = boardService.getContentInfo(content_idx);
		
		if (currentContentBean.getContent_writer_idx() != loginUserBean.getUser_idx()) {
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + "/board/not_writer");
			return false;
		}
		
		return true;
	}
	
}
