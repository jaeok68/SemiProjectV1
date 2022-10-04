package ljo.spring.mvc.dao;

import java.util.List;

import ljo.spring.mvc.vo.BoardVO;

public interface BoardDAO {

	int insertBoard(BoardVO bvo);

	List<BoardVO> selectBoard();

	BoardVO selectOneBoard(String bno);


}
