package ljo.spring.mvc.dao;

import java.util.Collections;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import ljo.spring.mvc.vo.MemberVO;

@Repository("mdao")
public class MemberDAOImpl implements MemberDAO {

    //@Autowired //bean태그에 정의한 경우 생략가능
    //private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;    
    private NamedParameterJdbcTemplate jdbcNameTemplate;
    
    private RowMapper<MemberVO> memberMapper =
    		BeanPropertyRowMapper.newInstance(MemberVO.class);
    
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
    public MemberVO selectOneMember() {
    	String sql = "select userid, name, email, regdate from member where mno = 1 ";
    	
    	return jdbcNameTemplate.queryForObject(sql, Collections.emptyMap(), memberMapper);
    }
    
    
}