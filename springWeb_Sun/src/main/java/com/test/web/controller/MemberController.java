package com.test.web.controller;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.test.web.service.MemberService;
import com.test.web.vo.MemberVO;

@Controller
@RequestMapping("/member")
public class MemberController {
	@RequestMapping(value = "signupForm", method ={RequestMethod.GET, RequestMethod.POST}) 
	public String signupFrom(){ 
		
		return "/member/signupForm"; //signupForm.jsp로!!
	}
	@Autowired
	private MemberService service;
	
	@RequestMapping(value = "memberInsert", method ={RequestMethod.GET, RequestMethod.POST}) 
	public String memberInsert(MemberVO vo, Model model){ 
		boolean flag = service.memberInsert(vo); //인자값ㅇ르 서비스의 memberInsert로!
		System.out.println(vo);
		model.addAttribute("vo",vo);
		model.addAttribute("result",flag);
		return "main"; //main.jsp로!!
	}
	/*@RequestMapping(value = "signup", method = {RequestMethod.GET, RequestMethod.POST})
	public String signup(MemberVO vo, Model model) { //화면에 데이터를 보이기 위해서는 model객체가 필요하낟!
			System.out.println(vo);
			model.addAttribute("vo",vo);
		return "main";
	}*/
	@RequestMapping(value = "login", method ={RequestMethod.GET, RequestMethod.POST}) 
	public String login(){ 
		
		return "/member/login"; //signupForm.jsp로!!
	}
	@RequestMapping(value = "memberLogin", method ={RequestMethod.GET, RequestMethod.POST}) 
	public String memberLogin(MemberVO vo, Model model, HttpSession session){ 
		boolean flag = service.memberLogin(vo); //인자값ㅇ르 서비스의 memberInsert로!
		if(flag == false){ 
			System.out.println(flag);
			System.out.println(vo);
			model.addAttribute("fail", "ID 혹은 PASSWORD가 일치하지 않습니다.");
			return "/member/login";
		}else{
			session.setAttribute("member", vo);
			session.setAttribute("id", vo.getUserid());
			System.out.println(flag);
			System.out.println(vo);
			model.addAttribute("vo1", vo);
			return "main"; //main.jsp로!!
		}
	}
	
	@RequestMapping(value = "logout", method ={RequestMethod.GET, RequestMethod.POST}) 
	public String logout(HttpSession session){
		
		session.removeAttribute("member");
		session.removeAttribute("id");//세션삭제
		return "main"; //main.jsp로!!
	}
}
