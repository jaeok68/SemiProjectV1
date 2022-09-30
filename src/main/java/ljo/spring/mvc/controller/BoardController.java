package ljo.spring.mvc.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import ljo.spring.mvc.service.BoardService;
import ljo.spring.mvc.vo.BoardVO;

@Controller
public class BoardController {
	
	private Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private BoardService brsv;

	@GetMapping("/list")
	public String list() {
		return "board/list";
	}
	
	@GetMapping("/view")	
	public String view() {
		return "board/view";
	}	
	
	@GetMapping("/write")
	public String write() {
		return "board/write";
	}	
	
	@PostMapping("/write")
	public String writeok(BoardVO bvo) {
		
		//새글쓰기 저장
		if (brsv.newBoard(bvo))
			LOGGER.info("새글쓰기 성공~!!");		
		
		return "redirect:/list";
	}		
	
}
