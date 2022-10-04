package ljo.spring.mvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ljo.spring.mvc.dao.BoardDAO;
import ljo.spring.mvc.vo.BoardVO;

@Service("bsrv")
public class BoardServiceImpl implements BoardService{

    @Autowired
    private BoardDAO bdao;
    
    @Override
    public boolean newBoard(BoardVO bvo) {
        boolean isInsert = false;
        
        // 글쓰기 성공시 true 리턴
        if (bdao.insertBoard(bvo) > 0) isInsert = true; 
        
        return isInsert;
    }

	@Override
	public List<BoardVO> readBoard(int snum) {

		return bdao.selectBoard(snum);
	}

	@Override
	public BoardVO readOneBoard(String bno) {
		
		return bdao.selectOneBoard(bno);
	}

	
    
}
