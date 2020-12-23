package kr.co.softcampus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")	//board로 가는 페이지는 이쪽에서 컨트롤한다
public class BoardController {
	
	@GetMapping("/main")
	public String main() {
		
		return "board/main";
	}
	
	@GetMapping("/read")
	public String read() {
		
		return "board/read";
	}
	
	@GetMapping("/write")
	public String wirte() {
		
		return "board/write";
	}
	
	@GetMapping("/modify")
	public String modify() {
		
		return "board/modify";
	}
	
	@GetMapping("/delete")
	public String delete() {
		
		return "board/delete";
	}
}
