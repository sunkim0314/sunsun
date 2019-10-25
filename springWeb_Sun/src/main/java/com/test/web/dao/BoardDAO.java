package com.test.web.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.test.web.vo.BoardVO;
import com.test.web.vo.ReplyVO;

@Repository
public class BoardDAO {
	
	@Autowired
	private SqlSession sqlSession;
	
	public ArrayList<BoardVO> boardList(HashMap<String,String> map, int startRecord, int countPerPage){//quickfix로 생성된 인자명 바꾸기
		BoardMapper mapper = sqlSession.getMapper(BoardMapper.class);
		RowBounds rb = new RowBounds(startRecord, countPerPage); //mybatis가 알아서 select쿼리 실행해줌 => 전체 게시물 다 뽑아오고 내가 원하는 게시물갯수만큼 뽑아서 바환시켜줌! 
		
		return mapper.boardList(map,rb);
		
	}

	public BoardVO boardRead(int boardNum) {
		BoardMapper mapper = sqlSession.getMapper(BoardMapper.class);
		mapper.countHit(boardNum);

		return mapper.boardRead(boardNum);
	}

	public int boardDelete(BoardVO vo) {
		BoardMapper mapper = sqlSession.getMapper(BoardMapper.class);
		return mapper.boardDelete(vo);
	}

	public int boardWrite(BoardVO vo) {
		BoardMapper mapper = sqlSession.getMapper(BoardMapper.class);
		return mapper.boardWrite(vo);
	}

	public int boardUpdate(BoardVO vo) {
		BoardMapper mapper = sqlSession.getMapper(BoardMapper.class);
		return mapper.boardUpdate(vo);
	}

	public int replyWrite(ReplyVO vo) {
		ReplyMapper mapper = sqlSession.getMapper(ReplyMapper.class);
		int result = mapper.replyWrite(vo);
		
		return result;
	}

	public ArrayList<ReplyVO> replyList(int boardNum) {
		ReplyMapper mapper = sqlSession.getMapper(ReplyMapper.class);
		return mapper.replyList(boardNum);
	}

	public void replyUpdate(ReplyVO vo) {
		ReplyMapper mapper = sqlSession.getMapper(ReplyMapper.class);
		mapper.replyUpdate(vo);
	}

	public int replyDelete(ReplyVO vo) {
		ReplyMapper mapper = sqlSession.getMapper(ReplyMapper.class);
		return mapper.replyDelete(vo);
	}

	public int getTotal(HashMap<String, String> map) {
		BoardMapper mapper = sqlSession.getMapper(BoardMapper.class);
		
		return mapper.getTotal(map);
	}
}
