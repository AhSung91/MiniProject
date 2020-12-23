package kr.co.softcampus.config;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.core.io.FileSystemResource;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

public class SpringConfigClass implements WebApplicationInitializer {
	/* WebApplicationInitializer : web.xml에 적었던 내용을 자바파일로 적을수 있게해준다 
	 * 							      웹이 실행되면 onstartUp메서드가 실행
	 * */
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		// TODO Auto-generated method stub
		//Spring MVC 프로젝트 설정을 위해 작성하는 클래스의 객체를 생성한다.
		AnnotationConfigWebApplicationContext servlet1 = new AnnotationConfigWebApplicationContext();
		
		//DispatcherServlet 경로 지정
		servlet1.register(ServletAppContext.class);
		
		
		//요청 발생 시 요청을 처리하는 서블릿을 DispatcherServlet으로 설정해준다.
		DispatcherServlet dis = new DispatcherServlet(servlet1);
		ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcher", dis);
	
		
		//부가설정(모든요청을 가장먼저 받겠다)
		servlet.setLoadOnStartup(1);
		servlet.addMapping("/");	
	
		//bean을 정의하는 클래스를 지정한다
		AnnotationConfigWebApplicationContext rootAppContext = new AnnotationConfigWebApplicationContext();
		rootAppContext.register(RootAppContext.class);
		
		ContextLoaderListener listener = new ContextLoaderListener(rootAppContext);
		servletContext.addListener(listener);
		
		//파라미터 인코딩 설정
		//dispatcher란 이름으로 등록된 servlet이 받아드리는 요청에 대해 인코딩필터를 통과시키는 설정
		FilterRegistration.Dynamic filter = servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);
		filter.setInitParameter("encoding", "UTF-8");
		filter.addMappingForServletNames(null, false, "dispatcher");
		
	}
}
