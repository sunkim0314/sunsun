package com.test.web.controller;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.test.web.service.TestService;
import com.test.web.vo.TestVO;
@Controller
@RequestMapping("/test")
public class TestController {
	
	@Autowired
	private TestService service;
	
	@RequestMapping(value = "testInsert", method ={RequestMethod.GET, RequestMethod.POST}) 
	public String testInsert(TestVO vo){ 
		service.testInsert(vo); //인자값ㅇ르 서비스의 testInsert로!
		return "main"; //main.jsp로!!
	}

	@RequestMapping(value = "testSession1", method ={RequestMethod.GET, RequestMethod.POST}) 
	public String testSession1(HttpSession session){ 
		session.setAttribute("test", "세션저장!");
		return "main"; //main.jsp로!!
	}
	
	@RequestMapping(value = "testSession2", method ={RequestMethod.GET, RequestMethod.POST}) 
	public String testSession2(HttpSession session){ 
		session.removeAttribute("test"); //세션삭제
		return "main"; //main.jsp로!!
	}
}
