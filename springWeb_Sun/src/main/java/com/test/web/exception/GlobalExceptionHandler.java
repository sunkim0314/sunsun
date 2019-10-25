package com.test.web.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(NullPointerException.class)
	public String nullpointerHandler(Model model,NullPointerException ex){
		model.addAttribute("msg", "널 에러 발생");
		model.addAttribute("ex", ex);
		
		return "exception/error";
		
	}
	
	@ExceptionHandler(Exception.class) //404error발생시  다음의 메서드를  수행해준다
	public String ExceptionHandler(Model model, Exception ex){
		model.addAttribute("msg", "존재하지 않는 페이지입니다.");
		model.addAttribute("ex", ex);
		
		return "exception/error";
		
	}

}
