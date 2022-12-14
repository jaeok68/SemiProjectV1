package ljo.spring.mvc.controller;


import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.apache.hc.core5.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ljo.spring.mvc.service.BoardService;
import ljo.spring.mvc.utils.RecaptchaUtils;
import ljo.spring.mvc.vo.BoardVO;

@Controller
public class BoardController {
	
	private Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	// DI받을 변수가 둘 이상이므로 생성자로 DI받도록 재정의
	//@Autowired	private BoardService bsrv;
	//@Autowired	private RecaptchaUtils grcp;
	private BoardService bsrv;
	private RecaptchaUtils grcp;
	
	@Autowired
	public BoardController(BoardService bsrv, RecaptchaUtils grcp) {
		this.bsrv = bsrv;
		this.grcp = grcp;
	}
	
	/* 페이징 처리 */
	/* 페이지당 게시물 수 perPage : 25 */
	/* 총 페이지수 : 전체게시글 / 페이지당 게시물 수 */
	/* 총 페이지수pages : ceil(getTotalPage / perPage ) */
	/* 2 = 50 / 25, 3 = 51 / 25 */
	/* 페이지별 읽어올 게시글 범위 */
	/* 총 게시글이 55건이라 할때 */
	/* 1page : 1번째 ~ 25번째 게시글 읽어옴 */
	/* 2page : 26번째 ~ 50번째 게시글 읽어옴 */
	/* 3page : 51번째 ~ 75번째 게시글 읽어옴 */
	/* ... */
	/* ipage : m번째 ~ n번째 게시글 읽어옴*/
	/* m = ( i - 1 ) * 25 + 1 */
	
	/* 현재 페이지에 따라서 보여줄 페이지 블럭 결정 */
	/* ex) 총 페이지수가 27일때 */
	/* cpg = 1 : 1 2 3 4 5 6 7 8 9 10 */
	/* cpg = 5 : 1 2 3 4 5 6 7 8 9 10 */
	/* cpg = 9 : 1 2 3 4 5 6 7 8 9 10 */
	/* cpg = 10 : 1 2 3 4 5 6 7 8 9 10 */
	/* cpg = 11 : 11 12 13 14 15 16 17 18 19 20 */
	/* cpg = 17 : 11 12 13 14 15 16 17 18 19 20 */
	/* cpg = 23 : 21 22 23 24 25 26 27 */
	/* cpg = 26 : 21 22 23 24 25 26 27 */
	/* cpg = n : ?+0 ?+1 ?+2 .. ?+9 */
	/* stpgn = ((cpg-1)/10)*10 + 1 */

	@GetMapping("/list")
	public String list(Model m, String cpg, String fkey, String fval) {
		
		int perPage = 25;
		if (cpg == null || cpg.equals("")) cpg = "1";
		if (fkey == null)  fkey = "";
		int cpage = Integer.parseInt(cpg);
		int snum = (cpage -1) * perPage;
		int stpgn = ((cpage-1)/10)*10 + 1;
		
		m.addAttribute("pages", bsrv.readCountBoard(fkey, fval));
		m.addAttribute("bdlist", bsrv.readBoard(fkey, fval, snum));
		m.addAttribute("stpgn", stpgn);
		m.addAttribute("fqry", "&fkey=" + fkey + "&fval=" + fval);
		//m.addAttribute("cpg", cpage);
		
		return "board/list";
	}
	
	@GetMapping("/view")	
	public ModelAndView view(ModelAndView mv, String bno) {
		
		mv.setViewName("board/view");
		mv.addObject("bd", bsrv.readOneBoard(bno));
		
		return mv;
	}	
	
	@GetMapping("/write")
	public String write(HttpSession sess) {
		String returnPage = "board/write";
		
		if (sess.getAttribute("m") == null) 
			returnPage = "redirect:/login";
		
		return returnPage;
	}	
	
	// captcha 작동원리
	// captcha 사용시 클라이언트가 생성한 키와
	// 서버에 설정해 둔 (비밀)키등을
	// google의 siteverify에서 비교해서
	// 인증에 성공하면 list로 redirect하고
	// 그렇지 않으면 다시 write로 return함
	// 질의를 위한 질의문자열은 다음과 같이 작성
	// ?secret=비밀키&response=클라이언트응답키
	@PostMapping("/write")
	public String writeok(BoardVO bvo, String gcaptcha, 
			RedirectAttributes rda) throws ParseException, IOException {
		//LOGGER.info(gcaptcha);
		String returnPage = "redirect:/write";
		
		if (grcp.checkCaptcha(gcaptcha)) {
			bsrv.newBoard(bvo);
			returnPage = "redirect:/list?cpg=1";
		}else {
			rda.addFlashAttribute("bvo", bvo);
			rda.addFlashAttribute("msg", "자동가입방지 확인이 실패했어요!");
		}
		
		return returnPage;
	}
	
    @GetMapping("/del")
    public String remove(HttpSession sess, String bno) {
    	String returnPage = "redirect:/list?cpg=1";
    	
        if (sess.getAttribute("m") == null)
        	returnPage = "redirect:/login";
        else
        	bsrv.removeBoard(bno);
        
        return returnPage;
    }
    
    @GetMapping("/upd")
    public String modify(HttpSession sess, String bno, Model m) {
    	String returnPage = "board/update";
    	
    	if (sess.getAttribute("m") == null)
        	returnPage = "redirect:/login";
    	else
    		m.addAttribute("bd", bsrv.readOneBoard(bno)) ;
        
        return returnPage;
    }    
    
    @PostMapping("/upd")
    public String modifyok(HttpSession sess, BoardVO bvo) {
    	String returnPage = "redirect:/view?bno=" +  bvo.getBno();
    	
    	if (sess.getAttribute("m") == null)
        	returnPage = "redirect:/login";
    	else
    		bsrv.modifyBoard(bvo) ;
        
        return returnPage;
    }        
	
}
