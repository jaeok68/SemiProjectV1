package ljo.spring.mvc.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import ljo.spring.mvc.vo.MemberVO;

public interface MemberService {
	
	boolean newMember(MemberVO mvo);

	MemberVO readOneMember(String uid);

	boolean checkLogin(MemberVO mvo);

	String checkUid(String uid);

	String findZipcode(String dong) throws JsonProcessingException;
}
