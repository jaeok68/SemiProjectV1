package ljo.spring.mvc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ljo.spring.mvc.vo.BoardVO;

@Repository("bdao")
public class BoardDAOImpl implements BoardDAO{

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public int insertBoard(BoardVO bvo) {
        String sql = "insert into board(title,userid,contents) values(?,?,?)";
        
        Object[] params = new Object[] {
        		bvo.getTitle(), bvo.getUserid(), 
        		bvo.getContents()
        };
        	
        
        
        return jdbcTemplate.update(sql, params);
    }
}
