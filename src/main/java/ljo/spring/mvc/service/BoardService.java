package ljo.spring.mvc.service;

import java.util.List;

import ljo.spring.mvc.vo.BoardVO;

public interface BoardService {

	boolean newBoard(BoardVO mbo);

	List<BoardVO> readBoard();

	
}
