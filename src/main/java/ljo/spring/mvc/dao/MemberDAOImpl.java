package ljo.spring.mvc.dao;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ljo.spring.mvc.vo.MemberVO;
import ljo.spring.mvc.vo.Zipcode;

@Repository("mdao")
public class MemberDAOImpl implements MemberDAO {

    @Autowired
    private SqlSession sqlSession;
     
     @Override
    public int insertMember(MemberVO mvo) {
    	return sqlSession.insert("member.insertMember", mvo);
    }
    
    @Override
    public MemberVO selectOneMember(String uid) {

    	return sqlSession.selectOne("member.selectOneMember", uid);
    }

	@Override
	public int selectOneMember(MemberVO m) {
		return sqlSession.selectOne("member.selectCountMember", m);
	}

	@Override
	public int selectCountUserid(String uid) {
		return sqlSession.selectOne("member.selectCountUserid", uid);
	}

	@Override
	public List<Zipcode> selectZipcode(String dong) {
		return sqlSession.selectList("member.selectZipcode", dong);
	}
 
    
}