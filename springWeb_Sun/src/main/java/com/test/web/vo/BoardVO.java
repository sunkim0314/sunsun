package com.test.web.vo;

import lombok.Data;

@Data
public class BoardVO {
	
	
	private int boardNum;
	private String userid;
	private String title;
	private String content;
	private String inputdate;
	private String originalFilename;
	private String savedFilename;
	private int hit;
	
	
}
