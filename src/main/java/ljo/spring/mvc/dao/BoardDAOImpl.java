package ljo.spring.mvc.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ljo.spring.mvc.vo.BoardVO;

@Repository("bdao")
public class BoardDAOImpl implements BoardDAO{
   
    @Autowired
    private SqlSession sqlSession;    
       
    @Override
    public int insertBoard(BoardVO bvo) {
    	return sqlSession.insert("board.insertBoard", bvo);
    }    
    
    @Override
    public List<BoardVO> selectBoard(String fkey, String fval, int snum){
    	
    	Map<String, Object> params = new HashMap<>();
    	params.put("fkey", fkey);
    	params.put("fval", fval);
    	params.put("snum", snum);
    	
    	return sqlSession.selectList("board.selectBoard", params);
    }

    @Override
    public BoardVO  selectOneBoard(String bno) { 
    	//본문글에 대한 조회수 증가시키기
    	sqlSession.update("board.viewBoard", bno);
    	
    	//본문글 가져오기
    	return sqlSession.selectOne("board.selectOneBoard", bno);
    }  

	@Override
	public int selectCountBoard(String fkey, String fval) {
    	Map<String, Object> params = new HashMap<>();
    	params.put("fkey", fkey);
    	params.put("fval", fval);
    	
		return sqlSession.selectOne("board.selectCountBoard", params);
	}
	
   @Override
    public int deleteBoard(String bno) { 
  
    	return sqlSession.delete("board.deleteBoard", bno);
    }  	

   @Override
   public int updateBoard(BoardVO bvo) {
	   
	   	return sqlSession.update("board.updateBoard", bvo);
   }
	
  
}
