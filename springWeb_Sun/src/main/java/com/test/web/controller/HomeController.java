package com.test.web.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//import com.test.web.vo.MemberVO;
import com.test.web.vo.TestVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 *	<beans:property name="prefix" value="/WEB-INF/views/" />
		<beans:property name="suffix" value=".jsp" />
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		
		return "main"; // 원래: /WEB-INF/views/main.jsp
	}
	
	
	//FOR ERROR
	@RequestMapping(value = "nullpoint", method = RequestMethod.GET)
	public String nullpoint() {
		 throw new NullPointerException();
	}
	
	
	
	@RequestMapping(value = "send1", method = RequestMethod.GET) //value�� send1�̶�� �ٲ۴�
	public String send1(String a, int b ) { //method�̸��� send1����!
		System.out.println("a : "+a);
		System.out.println("b : "+b);
		return "index"; //method���� ������ ������ index.jsp�� ���ư��ڴ�.
	}

	@RequestMapping(value = "send2", method = {RequestMethod.GET, RequestMethod.POST}) //{RequestMethod.GET, RequestMethod.POST} get�̵� post �Ѵ� ����
	public String send2(String a, int b ) { //method�̸��� send1����!
		System.out.println("a : "+a);
		System.out.println("b : "+b);
		return "index"; //method���� ������ ������ index.jsp�� ���ư��ڴ�.
	}
	
	@RequestMapping(value = "send3", method = RequestMethod.POST) //value�� send3�̶�� �ٲ۴�
	public String send3(String a, int b ) { //method�̸��� send1����!
		System.out.println("a : "+a);
		System.out.println("b : "+b);
		return "index"; //method���� ������ ������ index.jsp�� ���ư��ڴ�.
	}
	
	@RequestMapping(value = "send4", method =  {RequestMethod.GET, RequestMethod.POST}) //a�±� ==> get���
	public String send4(TestVO vo ) { //VO�� �ޱ�!!
		System.out.println(vo);
		return "index"; //method���� ������ ������ index.jsp�� ���ư��ڴ�.
	}
	
	/*@RequestMapping(value = "send5", method = RequestMethod.POST) //a�±� ==> get���
	public String send5(TestVO vo ) { //VO�� �ޱ�!!
		System.out.println(vo);
		return "index"; //method���� ������ ������ index.jsp�� ���ư��ڴ�.
	}*/
	
	@RequestMapping(value = "send5", method = RequestMethod.GET) //a�±� ==> get���
	public String send5(Model model ) { //VO�� �ޱ�!!
		model.addAttribute("data", "test"); //Map에서 Key와 Value
		model.addAttribute("vo",new TestVO("aaaa",2222));
		return "index"; //method안의 로직이 끝나면 index.jsp로 돌아가겠당.
	}
	/*
	@RequestMapping(value = "member/signupForm", method ={RequestMethod.GET, RequestMethod.POST}) //a�±� ==> get���
	public String signupFrom(){ 
		
		return "signupForm"; //signupForm.jsp로!!
	}
	@RequestMapping(value = "member/signup", method = {RequestMethod.GET, RequestMethod.POST})
	public String signup(MemberVO vo, Model model) { //화면에 데이터를 보이기 위해서는 model객체가 필요하낟!
			System.out.println(vo);
			model.addAttribute("vo",vo);
		return "main";
	}*/
}
