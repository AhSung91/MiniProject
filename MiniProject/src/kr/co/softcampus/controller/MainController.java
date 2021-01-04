package kr.co.softcampus.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.softcampus.beans.BoardInfoBean;
import kr.co.softcampus.beans.ContentBean;
import kr.co.softcampus.service.MainService;
import kr.co.softcampus.service.TopMenuService;

@Controller	//기본 페이지로 가는 컨트롤은 여기서 한다
public class MainController {
	
	@Autowired
	private MainService mainService;
	
	@Autowired
	private TopMenuService topMenuService;
	
	@GetMapping("main")
	public String main(Model model) {
		
		//List = 하나의 게시판에서 5개의 글을 담는다
		//ArrayList = 전체 게시판들을 담는다
		
		ArrayList<List<ContentBean>> list = new ArrayList<List<ContentBean>>();
		
		for (int i = 1; i <= 4; i++) {
			List<ContentBean> list1 = mainService.getMainList(i);
			list.add(list1);
		}
		
		model.addAttribute("list",list);
		
		List<BoardInfoBean> board_list = topMenuService.getTopMenuList();
		model.addAttribute("board_list",board_list);
		
		return "main";
	}
}
