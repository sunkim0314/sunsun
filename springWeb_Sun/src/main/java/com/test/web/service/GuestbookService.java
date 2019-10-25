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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.test.web.dao.GuestbookDAO;
import com.test.web.vo.GuestbookVO;

@Service
public class GuestbookService {
	
	@Autowired
	private GuestbookDAO dao;
	
	public ArrayList<GuestbookVO> guestbookList(String searchItem, String searchKeyword){
		/*
		 vo객체에다 한꺼번에 가져가는 것이 아니라 hash map을 사용한다.
		 */
		System.out.println("Service");
		HashMap<String, String>map=new HashMap<>();
		map.put("searchItem", searchItem);
		map.put("searchKeyword", searchKeyword);
		return dao.guestbookList(map);
	}

	public boolean write(GuestbookVO vo, MultipartFile uploadFile) {
		//파일등록을 하였는가 확인하는 메소드  등록 안했다면 TRUE
		if(!uploadFile.isEmpty()){
		 	String originalFilename = uploadFile.getOriginalFilename();
		 	//UUID 클래스: 고유한값을 생성할때 사용한다 ,RANDOMUUID: 숫자, 영어 소문자, 하이픈을 조합해 36글자의 고유한 문자열을 생성한다
		 	String savedFilename = UUID.randomUUID().toString(); 
		 	//원래 파일명 실제로 서버에 저장된 파일명을 한꺼번에 데이터베이스에 저장한다.
		 	
		 	//GuestbookVO에 새로운 필드 2가지 추가!
		 	vo.setOriginalFilename(originalFilename);
		 	vo.setSavedFilename(savedFilename);
		 	try {
				uploadFile.transferTo(new File("C:/test/"+savedFilename));
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} //사용자 컴퓨터에 있는것을 서버에 저장시켜줌;; transferTo(파일이 저장된 경로)
		}
		
		if( dao.write(vo) != 1) return false;
		return true;
	}

	public boolean delete(GuestbookVO vo) {
		//파일명을 알려면 select로 가져올 수 밖에 없다. 왜냐하면 방명록에는 특정게시물만 가져오는 메서드 없어서
		GuestbookVO guestbookVO = dao.read(vo);
		String savedFilename = guestbookVO.getSavedFilename();
		
		//제대로 실행 안되었을때 
		if(dao.delete(vo) != 1) return false; 
		
		//제대로 실행이 되었다면,
		 File file = new File("C:/test/"+savedFilename);
		 if(file.exists()) file.delete(); //게시글에 파일이 존재하는지 안하는지 확인하고 있으면 삭제
		 //존재하지 않는데 삭ㄷ제하려고 하면 에러가 터진다.
		 
		return true;
	}

	public GuestbookVO read(GuestbookVO vo) {
		return dao.read(vo);
	}

	public void download(GuestbookVO guestbookVO, HttpServletResponse response) {
		//링크를 클릭했을때 서버에 저장된 파일명을 알아야한다
		File file = new File("C:/test/"+ guestbookVO.getSavedFilename());
		//클라이언트의 컴퓨터에는 오리지날이름으로 저장돠어야한다
		String originalFilename = guestbookVO.getOriginalFilename();
		
		try {
			/*header: 정보를 담고 있음
			HttpServletResponse response ;클라이언트로 받은 요청을 처리한다음, 생성하고 전달하는 작업을 한다.
			setHeader: 응답에 대한 정보(를 수동으로 입력) 
			Content-Disposition: 응답의 종류를 지정 (ex 화면인지 or 다운로드 받을 파일받을지)
			1. attachment 응답의 형태(종류)가 다운로드 받을 파일의 이름이란것을
			2. 응답의 형태가 화면일 경우-> inline
			response.setHeader("Content-Disposition", "inline")
			response.addHeader("Content-type", "text/html;charset=utf-8");
			PrintWriter p =response.getWriter();
			p.write("<h1>직접작성한 화면</h1>");
			p.close();
			
			filename :사용자 컴퓨터에 아떤 이름으로 저장할지 쓴다  
			encoding; 한글 안깨지게 한다.
			 */			
			response.setHeader("Content-Disposition", 
						"attachment;filename=" + URLEncoder.encode(originalFilename,"utf-8"));
			//안할경우 문제 => 1. 원본파일의 크기보다 작게 저장이됨 (깨진파일이 저장이된다.) 2. 원본파일보다 더큰 크기로 저장된다  
			response.setContentLength((int)file.length()); 
			//copy(서버에 저장된 파일, 사용자 컴퓨터에 저장을 시켜주는 것) <=> copy(input, output)
			FileCopyUtils.copy(new FileInputStream(file), response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
