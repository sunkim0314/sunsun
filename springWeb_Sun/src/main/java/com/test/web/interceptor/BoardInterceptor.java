package com.test.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class BoardInterceptor extends HandlerInterceptorAdapter{
	//alt shift s => v
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("게시판 preHandle");
		//servlet JSP영역
		HttpSession session = request.getSession(); //HttpServletRequest에서 getSession으로 세션 가져오기
		//세션영역의 유저아이디가 존재하냐 존재하지않으냐 
		String id = (String) session.getAttribute("id");
		//아이디가 널이아니면 그댱 단계 진행
		if(id != null) return true;
		//아이디가 널이면 로그인화면으로!!
		response.sendRedirect("/web/member/login"); 
		return false;
	}
}
