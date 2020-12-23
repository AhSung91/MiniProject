package kr.co.softcampus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")	//user에 관련된 컨트롤은 여기서 처리
public class UserController {
	@GetMapping("/login")
	public String login() {
		
		return "user/login";
	}
	@GetMapping("/join")
	public String join() {
		
		return "user/join";
	}
	
	@GetMapping("/modify")
	public String modify() {
		
		return "user/modify";
	}
	
	@GetMapping("/logout")
	public String logout() {
		
		return "user/logout";
	}
}
