package com.test.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.web.dao.MemberDAO;
import com.test.web.vo.MemberVO;


@Service
public class MemberService {
	@Autowired //스프링컨테이너에 저장되었는지 찾아보고 객체를 저장해준다.
	private MemberDAO dao;
	
/*	public void memberInsert(MemberVO vo){
		int result = dao.memberInsert(vo);
		System.out.println(result);//입력성공한 행의 갯수
		if(result == 1){
			System.out.println("입력성공");
		}
	}*/
	public boolean memberInsert(MemberVO vo){
		if(dao.memberInsert(vo) != 1) return false;
		return true;
	}

	public boolean memberLogin(MemberVO vo) { //여기서 세션의 값을 집어넣어준다!
		if(dao.memberLogin(vo) != null) return true;
		return false; 
	}
}
