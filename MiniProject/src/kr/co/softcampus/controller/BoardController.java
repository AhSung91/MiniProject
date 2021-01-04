package kr.co.softcampus.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.softcampus.beans.ContentBean;
import kr.co.softcampus.beans.PageBean;
import kr.co.softcampus.beans.UserBean;
import kr.co.softcampus.service.BoardService;

@Controller
@RequestMapping("/board")	//board로 가는 페이지는 이쪽에서 컨트롤한다
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@Resource(name = "loginUserBean")	//로그인한 사람의 정보
	private UserBean  loginUserBean;
	
	@GetMapping("/main")
	public String main(@RequestParam("board_info_idx") int board_info_idx,
					   @RequestParam(value = "page",defaultValue = "1")int page,
						Model model) {
		model.addAttribute("board_info_idx",board_info_idx);
		
		String boardInfoName = boardService.getBoardInfoName(board_info_idx);
		model.addAttribute("boardInfoName",boardInfoName);
		
		List<ContentBean> contentList = boardService.getContentList(board_info_idx,page);
		model.addAttribute("contentList",contentList);
		
		PageBean pageBean = boardService.getContentCnt(board_info_idx, page);
		model.addAttribute("pageBean",pageBean);
		
		model.addAttribute("page",page);
		
		return "board/main";
	}
	
	@GetMapping("/read")
	public String read(@RequestParam("board_info_idx")int board_info_idx,
						@RequestParam("content_idx")int content_idx,
						@RequestParam("page")int page,
						Model model) {
		model.addAttribute("board_info_idx",board_info_idx); //목록보기때문에 값을 받아준다
		model.addAttribute("content_idx",content_idx);	//수정및 삭제를 눌렀을시 어떠한 글을 할지에 필요한 값
		
		ContentBean readContentBean = boardService.getContentInfo(content_idx);
		model.addAttribute("readContentBean", readContentBean);
		
		model.addAttribute("loginUserBean",loginUserBean);
		model.addAttribute("page",page);
		
		return "board/read";
	}
	
	@GetMapping("/write")
	public String write(@ModelAttribute("writeContentBean")ContentBean writeContentBean,
						@RequestParam("board_info_idx")int board_info_idx) {
		
		writeContentBean.setContent_board_idx(board_info_idx);
		
		return "board/write";
	}
	//글쓰기 유효성검사
	@PostMapping("/write_pro")
	public String write_pro(@Valid @ModelAttribute("writeContentBean")ContentBean writeContentBean,BindingResult result) {
		if (result.hasErrors()) {
			return "board/write";
		}
		
		boardService.addContentInfo(writeContentBean);
		return "board/write_success";
	}
	
	@GetMapping("/modify")
	public String modify(@RequestParam("board_info_idx")int board_info_idx,
						 @RequestParam("content_idx") int content_idx,
						 @ModelAttribute("modifyContentBean")ContentBean modifyContentBean,
						 @RequestParam("page")int page,
						 Model model) {
		
		model.addAttribute("board_info_idx",board_info_idx);
		model.addAttribute("content_idx",content_idx);
		
		ContentBean tempContentBean = boardService.getContentInfo(content_idx);
		
		modifyContentBean.setContent_writer_name(tempContentBean.getContent_writer_name());
		modifyContentBean.setContent_date(tempContentBean.getContent_date());
		modifyContentBean.setContent_subject(tempContentBean.getContent_subject());
		modifyContentBean.setContent_text(tempContentBean.getContent_text());
		modifyContentBean.setContent_file(tempContentBean.getContent_file());
		modifyContentBean.setContent_writer_idx(tempContentBean.getContent_writer_idx());
		modifyContentBean.setContent_board_idx(board_info_idx);
		modifyContentBean.setContent_idx(content_idx);
		
		model.addAttribute("page",page);
		
		return "board/modify";
	}
	
	@PostMapping("modify_pro")
	public String modify_pro(@Valid @ModelAttribute("modifyContentBean")ContentBean modifyContentBean,
							@RequestParam("page")int page,
							Model model,
							BindingResult result) {
		
		if (result.hasErrors()) {
			return "board/modify";
		}
		//수정 저장 처리
		boardService.modifyContentInfo(modifyContentBean);
		
		model.addAttribute("page",page);
		
		return "board/modify_success";
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam("board_info_idx")int board_info_idx,
						 @RequestParam("content_idx")int content_idx,
						 Model model) {
		boardService.deleteContentInfo(content_idx);
		model.addAttribute("board_info_idx" , board_info_idx);
		
		return "board/delete";
	}
	
	@GetMapping("not_writer")
	public String not_writer() {
		return "board/not_writer";
	}
}
