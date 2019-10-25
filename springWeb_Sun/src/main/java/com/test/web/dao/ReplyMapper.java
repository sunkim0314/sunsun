package com.test.web.dao;

import java.util.ArrayList;

import com.test.web.vo.ReplyVO;

public interface ReplyMapper {

	public int replyWrite(ReplyVO vo);

	public ArrayList<ReplyVO> replyList(int boardNum);

	public void replyUpdate(ReplyVO vo);

	public int replyDelete(ReplyVO vo);

}
