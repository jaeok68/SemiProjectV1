package ljo.spring.mvc.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import ljo.spring.mvc.vo.BoardVO;
import ljo.spring.mvc.vo.MemberVO;

@Repository("mdao")
public class MemberDAOImpl implements MemberDAO {

    //@Autowired //bean태그에 정의한 경우 생략가능
    //private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;    
    
    public MemberDAOImpl(DataSource dataSource) {
    	simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
				.withTableName("member")
				.usingColumns("userid","passwd","name","email");
    }
 
    @Override
    public int insertMember(MemberVO mvo) {
    	SqlParameterSource params = 
    			new BeanPropertySqlParameterSource(mvo);
    	
		return simpleJdbcInsert.execute(params);
    }
    
/*    
    @Override
    public int insertMember(MemberVO mvo) {
        String sql = "insert into member(userid,passwd,name,email) values(?,?,?,?)";
        
        Object[] params = new Object[] {
        		mvo.getUserid(), mvo.getPasswd(),
        		mvo.getName(), mvo.getEmail()
        };
        	
        
        return jdbcTemplate.update(sql, params);
    }
*/
    
}