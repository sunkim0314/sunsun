package com.test.web.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.test.web.vo.TestVO;

@Repository //Spring에 dao역할을 하는 class라는것을 알려준다 **상징적인 의미-> 궁극적인 의미는 클래스파일들을 컨테이너에서 관리하게 한다.
public class TestDAO {
	@Autowired //컨테이너에 올라간다.컨테이너에서 관리는 하지만 자동으로 객체를 생성시키지는 안한다. 따라서  root.contex sqlsession은 bean태그로 객체를 생성한다
	private SqlSession sqlSession; //sqlSession 필요하다.　<= root.context밑에 보면 id가 SQLSession! 컨테이너에서 관리하는 객체! 
	
	public int testInsert(TestVO vo){
		TestMapper mapper = sqlSession.getMapper(TestMapper.class); //TestMapper는 인터페이슨데 implements 안하고도 구동이 가능하다. why?
		//==> getMapper(TestMapper.class) = TestMapper.xml이 대신 객체를 생성해준다.
		return mapper.testInsert(vo);
	}
}
