package ljo.spring.mvc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import ljo.spring.mvc.vo.MemberVO;

@Repository("mdao")
public class MemberDAOImpl implements MemberDAO {

    @Autowired //bean태그에 정의한 경우 생략가능
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;    
    private NamedParameterJdbcTemplate jdbcNameTemplate;
    
 //   private RowMapper<MemberVO> memberMapper =
 //   		BeanPropertyRowMapper.newInstance(MemberVO.class);
    
    public MemberDAOImpl(DataSource dataSource) {
    	simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
				.withTableName("member")
				.usingColumns("userid","passwd","name","email");
    	
    	jdbcNameTemplate =
    			new NamedParameterJdbcTemplate(dataSource);
    }
 
    @Override
    public int insertMember(MemberVO mvo) {
    	SqlParameterSource params = 
    			new BeanPropertySqlParameterSource(mvo);
    	
		return simpleJdbcInsert.execute(params);
    }
    
    @Override
    public MemberVO selectOneMember(String uid) {
    	String sql = "select userid, name, email, regdate from member where userid = ?";
    	
    	Object[] param = { uid };
    	
    	RowMapper<MemberVO> memberMapper = (rs, num) -> {
			MemberVO m = new MemberVO();
			
			m.setUserid(rs.getString("userid"));
			m.setName(rs.getString("name"));
			m.setEmail(rs.getString("email"));
			m.setRegdate(rs.getString("regdate"));
			
			return m;
		};
		
		return jdbcTemplate.queryForObject(sql, param, memberMapper);
    }

	@Override
	public int selectOneMember(MemberVO m) {
		String sql = "select count(mno) cnt from member where userid = ? and passwd = ?";
		
		Object[] params = { m.getUserid(), m.getPasswd() };
		
		return jdbcTemplate.queryForObject(sql, params, Integer.class);
	}
    
    
    
}