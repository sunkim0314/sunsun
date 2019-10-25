package com.test.web.vo;

import lombok.Data;

@Data
public class ReplyVO {
	
	private int replynum;
	private int boardNum; //이댓글이 어떤 게시글에 등록된  것인가.
	private String userid;
	private String replytext;
	private String inputdate;
	
}
