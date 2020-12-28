package kr.co.softcampus.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.softcampus.beans.UserBean;
import kr.co.softcampus.service.UserService;
import kr.co.softcampus.validator.UserValidator;

@Controller
@RequestMapping("/user") // user에 관련된 컨트롤은 여기서 처리
public class UserController {

	@Autowired
	private UserService userService;
	
	@Resource(name = "loginUserBean")
	private UserBean loginUserBean;

	@GetMapping("/login")
	public String login(@ModelAttribute("tempLoginUserBean") UserBean tempLoginUserBean,
						@RequestParam(value = "fail",defaultValue = "false")boolean fail, Model model) {
		model.addAttribute("fail",fail);
		return "user/login";
	}

	// 로그인 유효성 검사 처리
	@PostMapping("login_pro")
	public String login_pro(@Valid @ModelAttribute("tempLoginUserBean") UserBean tempLoginUserBean,
			BindingResult result) {
		
		if (result.hasErrors()) {
			return "user/login";
		}
		//로그인 성공여부 확인
		userService.getLoginUserInfo(tempLoginUserBean);
		
		if (loginUserBean.isUserLogin() == true) {
			
			return "user/login_success"; //성공
		}else {
			return "user/login_fail";	//실패
		}
	}

	@GetMapping("/join")
	public String join(@ModelAttribute("joinUserBean") UserBean joinUserBean) {

		return "user/join";
	}

	@PostMapping("/join_pro") // 유효성 검사를 위한 메서드,이름을 joinUserBean으로 새로 정의
	public String join_pro(@Valid @ModelAttribute("joinUserBean") UserBean joinUserBean, BindingResult result) {

		if (result.hasErrors()) {
			return "user/join";
		}
		// 에러가 나타나지않으면

		userService.addUserInfo(joinUserBean); // 회원가입을 처리하는 정보를 저장

		return "user/join_success";
	}

	@GetMapping("/modify")
	public String modify() {

		return "user/modify";
	}

	@GetMapping("/logout")
	public String logout() {
		
		loginUserBean.setUserLogin(false);	//로그아웃하기위한 false값
		
		return "user/logout";
	}
	
	@GetMapping("/not_login")
	public String not_login() {
		
		return "user/not_login";
	}
	

	@InitBinder // UserValidator를 UserController에서 컨트롤하기 위해 등록
	public void initBinder(WebDataBinder binder) {
		UserValidator validator1 = new UserValidator();
		binder.addValidators(validator1);
	}
}
