package com.test.web.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.test.web.vo.GuestbookVO;

public interface GuestbookMapper {
	//method와 xml의 id값이랑 꼭!!! 같아야!!
	public ArrayList<GuestbookVO> guestbookList(HashMap<String, String> map);

	public int write(GuestbookVO vo);

	public int delete(GuestbookVO vo);

	public GuestbookVO read(GuestbookVO vo);
}
