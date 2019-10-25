package com.test.web.vo;

import lombok.Data;

@Data
public class GuestbookVO {
	private int seq;
	private String name;
	private String pwd;
	private String content;
	private String regdate;
	private String originalFilename;
	private String savedFilename;
 }
