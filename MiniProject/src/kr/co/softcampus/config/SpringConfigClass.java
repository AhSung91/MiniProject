package kr.co.softcampus.config;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.core.io.FileSystemResource;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/*public class SpringConfigClass implements WebApplicationInitializer {
	
	 * WebApplicationInitializer : web.xml에 적었던 내용을 자바파일로 적을수 있게해준다 웹이 실행되면
	 * onstartUp메서드가 실행
	 
	
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
		
	}*/
public class SpringConfigClass extends AbstractAnnotationConfigDispatcherServletInitializer{
	//DispatcherServlet에 매핑할 요청 주소를 셋팅한다
	@Override
	protected String[] getServletMappings() {
		// TODO Auto-generated method stub
		return new String[] {"/"};
	}
	//Spring MVC 프로젝트 설정을 위한 클래스를 지정한다
	@Override
	protected Class<?>[] getServletConfigClasses() {
		// TODO Auto-generated method stub
		return new Class[] {ServletAppContext.class};
	}
	
	//프로젝트에서 사용할 Bean들을 정의하기 위한 클래스를 지정한다.
	@Override
	protected Class<?>[] getRootConfigClasses() {
		// TODO Auto-generated method stub
		return new Class[] {RootAppContext.class};
	}
	
	//파라미터 인코딩 필터 설정
	@Override
	protected Filter[] getServletFilters() {
		// TODO Auto-generated method stub
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding("UTF-8");
		return new Filter[] {encodingFilter};
	}
	//multipostform데이터를 받기 위한 설정
	@Override
	protected void customizeRegistration(Dynamic registration) {
		// TODO Auto-generated method stub
		super.customizeRegistration(registration);
		
		//(클라이언트가 보낸 파일데이터를 저장해 놓는 저장하는 임시경로(null이면 톰켓에서 자동으로 지정),업로드하는 파일의 최대용량,업로드파일+전체요청정보용량,파일의임계값(0이면 알아서 저장하라)
		MultipartConfigElement config1 = new MultipartConfigElement(null, 52428800, 524288000,0);
		registration.setMultipartConfig(config1);
	}
}
