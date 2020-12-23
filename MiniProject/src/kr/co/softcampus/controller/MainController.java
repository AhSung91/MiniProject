package kr.co.softcampus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller	//기본 페이지로 가는 컨트롤은 여기서 한다
public class MainController {
	@GetMapping("main")
	public String main() {
		
		return "main";
	}
}
