package com.test.web.dao;

import com.test.web.vo.MemberVO;

public interface MemberMapper {
	public int memberInsert(MemberVO vo); //insert성공한 행의 갯수! 

	public MemberVO memberLogin(MemberVO vo); //resultType 과 같이! xml파일의 id값과 동일하게 method값 만들기 
	//=> mybatis.config => memberDAO
}
