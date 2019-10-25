package com.test.web.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.test.web.service.GuestbookService;
import com.test.web.vo.GuestbookVO;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {

		@Autowired
		private GuestbookService service;
		//첨부파일 다운로드 return값 void!!!! 
		//이때까지 스프링이 자동으로  return 값의 페이지로 반환시켜줫던것을 수동으로 처리하겠다 --> HttpServletResponse(수동으로 처리!)
		@RequestMapping(value = "download", method = RequestMethod.GET)
		public void download(GuestbookVO vo, HttpServletResponse response){ //데이터베이스에서 데이터 가죠오기
			GuestbookVO guestbookVO = service.read(vo);
			service.download(guestbookVO, response); 
		}
		
		//방명롯 글삭제
		@RequestMapping(value = "delete", method = RequestMethod.POST)
		public String delete(GuestbookVO vo, Model model){ //데이터베이스에서 데이터 가죠오기
			System.out.println(vo);
			boolean result = service.delete(vo); 
			model.addAttribute("deleteResult",result);
			return "forward:/guestbook/guestbookList";
		}
		
		//방명록 글등록
		@RequestMapping(value = "write", method = RequestMethod.POST)
		public String write(GuestbookVO vo, Model model, MultipartFile uploadFile){ //데이터베이스에서 데이터 가죠오기
			System.out.println(vo);
			boolean result = service.write(vo, uploadFile); 
			model.addAttribute("writeResult", result);
			//return "redirect:/guestbook/guestbookList"; //모델객체에 담은 데이터를 전달을 못할 가능성이 있다 ==> forward를 쓴다
			return "forward:/guestbook/guestbookList";  //post로 요청보냄
		}
		//redirect: 1)get방식으로  2) url을 클라이언트가 요청하게끔
		//forward: 1)post방식으로 2) 서버에서 서버로 요청보냄
		//방명록 리스트 읽어오기 ============> model.addAttribute("list", list)와 그전에 포워드한 	model.addAttribute("writeResult", result)도 갖고 있다.
		@RequestMapping(value = "guestbookList", method = {RequestMethod.GET, RequestMethod.POST})
		public String guestbookList(
				@RequestParam(value="searchItem", defaultValue="name") String searchItem, //화면의 input태그의 name속성값 ==> 화면으로부터 전달받은 parameter중에 존재했다면 넣어즈고 없으면 default 넣어줘라
				@RequestParam(value="searchKeyword", defaultValue="") String searchKeyword, 
				Model model){ //데이터베이스에서 데이터 가죠오기
			System.out.println("Controller");
			ArrayList<GuestbookVO> list = service.guestbookList(searchItem, searchKeyword);
			model.addAttribute("list", list); 
			return "/guestbook/guestbookList";
		}
		//메인페이지에서 방명록 페이지로
		@RequestMapping(value = "boardList", method ={RequestMethod.GET, RequestMethod.POST}) 
		public String boardList(){ 
			
			return "/board/boardList"; //boardList.jsp로!!
		}
}
