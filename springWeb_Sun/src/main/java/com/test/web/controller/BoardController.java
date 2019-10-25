package com.test.web.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.test.web.service.BoardService;
import com.test.web.util.PageNavigator;
import com.test.web.vo.BoardVO;
import com.test.web.vo.ReplyVO;
@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService service;
	
	//댓글 삭제
		@RequestMapping(value = "replyDelete", method = RequestMethod.GET)
		public String replyDelete(ReplyVO vo, HttpSession session){ //화면에서 가져온 2가지 값은 vo에 넣기
			service.replyDelete(vo, session);
	
			return "redirect:/board/boardRead?boardNum="+vo.getBoardNum();
		}
	
	//댓글 수정
	@RequestMapping(value = "replyUpdate", method = RequestMethod.GET)
	public String replyUpdate(ReplyVO vo, HttpSession session){ //화면에서 가져온 3가지 값은 vo에 넣기
		service.replyUpdate(vo, session);
		
		return "redirect:/board/boardRead?boardNum="+vo.getBoardNum(); //게시물번호르르 같이 보내주어야한다.
	}
	//댓글쓰기
	@RequestMapping(value = "replyWrite", method = RequestMethod.POST)
	public String replyWrite(ReplyVO vo, HttpSession session, RedirectAttributes rttr){ //데이터베이스에서 데이터 가죠오기
		service.replyWrite(vo, session);
		rttr.addFlashAttribute("boardNum", vo.getBoardNum());
		return "redirect:/board/boardRead?boardNum="+vo.getBoardNum();
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//게시물읽어오기
	@RequestMapping(value = "boardRead", method = RequestMethod.GET) //화면에서 보드넘을 받아온다.
	public String boardRead(int boardNum, Model model){ //jsp의${board.boardNum}과 이름 같아야 들어간다.
		BoardVO vo = service.boardRead(boardNum);
		model.addAttribute("vo", vo);
		ArrayList<ReplyVO> replylist= service.replyList(boardNum);
		model.addAttribute("replylist", replylist);
		
		return "/board/boardRead";
	}
	
	//model은 request영역에 저장된다.
	//RedirectAttribute: 한번사용하면 사라지는것
	
	//게시물 삭제 - 첨부파일도 같이 삭제되게!!
	@RequestMapping(value = "boardDelete", method = {RequestMethod.GET,RequestMethod.POST})
	public String boardDelete(BoardVO vo,/*Model model*/RedirectAttributes rttr, HttpSession session){ //데이터베이스에서 데이터 가죠오기
		boolean result = service.boardDelete(vo, session);
		//model.addAttribute("deleteResult", result);
		/*return "forward:/board/boardList"; //새로갱신된 게시판목록화면을 출력해야한다.! ==> redirect이용*/
		rttr.addFlashAttribute("deleteResult", result); //Flash:한번만 사용하고 버리는 것 //삭제성공메세지가 한번만 뜬다.
		return "redirect:/board/boardList";
	}

	//게시물 첨부파일  다운로드
	@RequestMapping(value = "boardDownload", method = RequestMethod.GET)
	public void boardDownload(int boardNum,HttpSession session,HttpServletResponse response){ //데이터베이스에서 데이터 가죠오기
		BoardVO vo = service.boardRead(boardNum);
		service.boardDownload(vo, response);
	}
	
	//게시물 수정 method
	@RequestMapping(value = "boardUpdate", method = RequestMethod.POST)
	public String boardUpdate(BoardVO vo,RedirectAttributes rttr, HttpSession session, MultipartFile uploadFile){ //데이터베이스에서 데이터 가죠오기
		boolean result = service.boardUpdate(vo, session,uploadFile);
		rttr.addFlashAttribute("updateResult", result);
		return "redirect:/board/boardRead?boardNum="+vo.getBoardNum(); //보드넘을 안넘기면 null로 입력되므로! 게시글 번호를 boardRead메소드의 인자값으로 들어간디.
	}
	//게시물 수정 페이지로
	@RequestMapping(value = "boardUpdateForm", method = RequestMethod.GET)
	public String boardUpdateForm(int boardNum, Model model){ 
		BoardVO vo =service.boardRead(boardNum);//특정게시물에대한 내용들 필요 // 특정게시물의 정보르르 그대로 가져온다.
		model.addAttribute("vo", vo);
		return "/board/boardUpdateForm"; 
	}
	//게시물 쓰기 method
	@RequestMapping(value = "boardWrite", method = RequestMethod.POST)
	public String boardWrite(BoardVO vo, RedirectAttributes rttr, HttpSession session, MultipartFile uploadFile){ 
		boolean result = service.boardWrite(vo,session,uploadFile);
		rttr.addFlashAttribute("writeResult", result);
		return "redirect:/board/boardList"; 
	}
	//게시물 등록페이지로! 화면이동만!
	@RequestMapping(value = "boardWriteForm", method = RequestMethod.GET)
	public String boardWriteForm(){ 
		return "/board/boardWriteForm"; 
	}

	
	//게시물리스트 불러오기
	@RequestMapping(value = "boardList", method = {RequestMethod.GET,RequestMethod.POST})
	public String boardList(Model model,
			@RequestParam(value="currentPage",defaultValue="1") int currentPage, //paging! 190718
			@RequestParam(value="searchItem",defaultValue="title") String searchItem,
			@RequestParam(value="searchKeyword",defaultValue="") String searchKeyword
			){ //데이터베이스에서 데이터 가죠오기
		PageNavigator navi = service.getNavi(currentPage, searchItem,searchKeyword);
		ArrayList<BoardVO> list = service.boardList(searchItem, searchKeyword,navi);
		model.addAttribute("list", list);
		///페이징기법에서 추가!
		model.addAttribute("navi", navi);
		model.addAttribute("searchItem", searchItem);
		model.addAttribute("searchKeyword", searchKeyword);
		return "/board/boardList";
	}
}
