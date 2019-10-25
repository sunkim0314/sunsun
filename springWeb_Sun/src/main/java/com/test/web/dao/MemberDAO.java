package com.test.web.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.test.web.vo.MemberVO;

@Repository
public class MemberDAO {
	@Autowired
	private SqlSession sqlSession;
	//회원가입 처리
	public int memberInsert(MemberVO vo) {
		MemberMapper mapper = sqlSession.getMapper(MemberMapper.class); 
		//멤버 매퍼 인터페이스는 xml에서 지정해주고 있다. interface와xml은 연결되어있다.
		//인자값으로 넣어주면 getMapper가 객체생성을 알아서 해준다.(메서드를 사용할수 있게 해준다.) 추상에서드를 알아서 mapper 라는 변수에다가 집어넣어준다. 
		//.class ==> AAA.class->  metadata를 반환시켜준다. ex) 메타데이터의 예: 필드값의 변수명, 클래스에 선언되어있는 메서드명
		//ex)이클립스의 자동완성기능--> 메타데이터를 기본으로 가능한다.
		return mapper.memberInsert(vo);
	
	}
	//로그인 처리
	public MemberVO memberLogin(MemberVO vo) {
		MemberMapper mapper = sqlSession.getMapper(MemberMapper.class);
			return mapper.memberLogin(vo); //=> memberService!
	}

}
