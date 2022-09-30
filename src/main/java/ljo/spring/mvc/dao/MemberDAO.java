package ljo.spring.mvc.dao;

import ljo.spring.mvc.vo.MemberVO;

public interface MemberDAO {

	int insertMember(MemberVO mvo);

	MemberVO selectOneMember();
	

}
