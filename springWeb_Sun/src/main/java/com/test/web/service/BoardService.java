package com.test.web.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.test.web.dao.BoardDAO;
import com.test.web.util.PageNavigator;
import com.test.web.vo.BoardVO;
import com.test.web.vo.ReplyVO;

@Service
public class BoardService {

	@Autowired
	private BoardDAO dao;

	private final int countPerPage = 10;
	private final int pagePerGroup = 5;

	public ArrayList<BoardVO> boardList(String searchItem, String searchKeyword, PageNavigator navi) {
		HashMap<String, String> map = new HashMap<>();
		map.put("searchItem", searchItem);
		map.put("searchKeyword", searchKeyword);
		return dao.boardList(map, navi.getStartRecord(), navi.getCountPerPage());
	}

	public BoardVO boardRead(int boardNum) {

		return dao.boardRead(boardNum);
	}

	public boolean boardDelete(BoardVO vo, HttpSession session) {
		String userid = (String) session.getAttribute("id");
		vo.setUserid(userid);
		// 첨부파일도 같이 삭제되게
		BoardVO boardVO = dao.boardRead(vo.getBoardNum());
		String savedFilename = boardVO.getSavedFilename();

		if (dao.boardDelete(vo) != 1)
			return false;

		File f = new File("C:/test/" + savedFilename);
		if (f.exists())
			f.delete();
		return true;
	}

	public boolean boardWrite(BoardVO vo, HttpSession session, MultipartFile uploadFile) {
		String userid = (String) session.getAttribute("id");
		vo.setUserid(userid); // 세션에서 가져온 아이디값 세팅

		if (!uploadFile.isEmpty()) { // 사용자가 첨부파일에 파일을 등록시켯을때

			String savedFilename = UUID.randomUUID().toString(); // 고유 문자열로 변경하여
																	// 집어넣어주기
			String originalFilename = uploadFile.getOriginalFilename();
			System.out.println("write" + savedFilename);
			vo.setOriginalFilename(originalFilename);
			vo.setSavedFilename(savedFilename);

			try { // 실제로 서버에다가 첨부파일을 저장하는 로직
				uploadFile.transferTo(new File("C:/test/" + savedFilename));
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (dao.boardWrite(vo) != 1) {
			return false;
		}
		return true;
	}

	public boolean boardUpdate(BoardVO vo, HttpSession session, MultipartFile uploadFile) {
		String userid = (String) session.getAttribute("id");
		vo.setUserid(userid); // 세션에서 가져온 아이디값 세팅

		String oldSavedFile = vo.getSavedFilename(); // 옛날 파일네임

		// 1. 먼저 업로드된파일이 null인지 아닌지 확인해야지 ===> 수정내용의 첨부파일이 등록되어 있는 경우;
		if (!uploadFile.isEmpty()) {

			// 2. vo객체의 새로운 첨부파일 이름을 세팅해준다
			// 고유문자로 만들어서 서버에 저장시킬 이름과 업로드 파일의 파일명을 저장!
			String savedFilename = UUID.randomUUID().toString();
			String originalFilename = uploadFile.getOriginalFilename();

			vo.setSavedFilename(savedFilename);
			vo.setOriginalFilename(originalFilename);

			File file = new File("C:/test/" + savedFilename);

			// 3. 실제로 서버에다가 첨부파일을 저장하는 로직 ==> Transferto + try catch 필요!!
			try {
				uploadFile.transferTo(file);
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			int result = dao.boardUpdate(vo);

			// update쿼리가 실패할 경우
			if (result != 1) {
				file.delete();
				return false;
			}

			// update 쿼리 성공한굥우
			File oldfile = new File("C:/test/" + oldSavedFile);

			// 수정 정 게시글에 첨부파일이 존재할 경우 삭제
			if (oldfile.exists())
				oldfile.delete();
			return true;
		}
		
		
		// 수정 내용레 첨부파일이 없는 경우
		if (dao.boardUpdate(vo) != 1)
			return false;
		return true;

	}

	public boolean replyWrite(ReplyVO vo, HttpSession session) {
		String userid = (String) session.getAttribute("id");
		vo.setUserid(userid); // 세션에서 가져온 아이디값 세팅
		if (dao.replyWrite(vo) != 1) {
			return false;
		}
		return true;
	}

	public ArrayList<ReplyVO> replyList(int boardNum) {
		return dao.replyList(boardNum);

	}

	public void replyUpdate(ReplyVO vo, HttpSession session) {
		// 수정 삭제할 때, 세션에서 아이디가져와서 vo에 전달 그vo를 dao에 전달
		String userid = (String) session.getAttribute("id");
		vo.setUserid(userid); // 세션에서 가져온 아이디값 세팅
		dao.replyUpdate(vo);
	}

	public boolean replyDelete(ReplyVO vo, HttpSession session) {
		String userid = (String) session.getAttribute("id");
		vo.setUserid(userid); // 세션에서 가져온 아이디값 세팅
		if (dao.replyDelete(vo) != 1) {
			return false;
		}
		return true;
	}

	public PageNavigator getNavi(int currentPage, String searchItem, String searchKeyword) {
		HashMap<String, String> map = new HashMap<>();
		map.put("searchItem", searchItem);
		map.put("searchKeyword", searchKeyword);
		int totalRecordsCount = dao.getTotal(map); // 게시물의 총갯수 넣기!
		PageNavigator navi = new PageNavigator(countPerPage, pagePerGroup, currentPage, totalRecordsCount);
		return navi;
	}

	public void boardDownload(BoardVO vo, HttpServletResponse response) {
		File f = new File("C:/test/" + vo.getSavedFilename());
		String originalFilename = vo.getOriginalFilename();

		try {
			response.setHeader("COntent-Disposition",
					" attachment;filename=" + URLEncoder.encode(originalFilename, "utf-8"));
			response.setContentLength((int) f.length());
			FileCopyUtils.copy(new FileInputStream(f), response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
