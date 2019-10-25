package com.test.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.web.dao.TestDAO;
import com.test.web.vo.TestVO;

@Service //서비스역할을 하는 클래스라는 것을 Spring에게 알려주는 것 !  ==> 객체생성을 하는 bean이다 but autowired는 아니다.
public class TestService {
	@Autowired
	private TestDAO dao;
	
	public void testInsert(TestVO vo) {
		int result = dao.testInsert(vo); //인자값ㅇ르 dao의 testInsert로!
		System.out.println(result);
	}
}
