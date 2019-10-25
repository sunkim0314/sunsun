package com.test.web.util;

import lombok.Data;

@Data
public class PageNavigator {
	
	private int countPerPage;		//페이지당 글목록 수
	private int pagePerGroup;		//그룹당 페이지 수  //아랫부분에 몇개의 페이지를 표시할 것인가. ==> 77/10 = 7*10+7 => 8개의 페이지가 나오는데 한화면에 5개의 페이지만 나오게한다. 
	private int currentPage;		//현재 페이지 (최근 글이 1부터 시작)
	private int totalRecordsCount;	//전체 글 수 --> 보드테이블에 저장되어있는 전체 게시글 갯수
	private int totalPageCount;		//전체 페이지 수  ==> 77/10 = 7*10+7 => 8개의 페이지
	private int currentGroup;		//현재 그룹 (최근 그룹이 0부터 시작)
	private int startPageGroup;		//현재 그룹의 첫 페이지
	private int endPageGroup;		//현재 그룹의 마지막 페이지
	private int startRecord;		//전체 레코드 중 현재 페이지 첫 글의 위치 (0부터 시작)
	
	/*
	 * 생성자
	 */
	public PageNavigator(int countPerPage, int pagePerGroup, int currentPage, int totalRecordsCount) {
		//페이지당 글 수, 그룹당 페이지 수, 현재 페이지, 전체 글 수를 전달받음
		this.countPerPage = countPerPage;
		this.pagePerGroup = pagePerGroup; 
		this.totalRecordsCount = totalRecordsCount;
		
		//전체 페이지 수
		totalPageCount = (totalRecordsCount + countPerPage - 1) / countPerPage;

		//전달된 현재 페이지가 1보다 작으면 현재페이지를 1페이지로 지정
		if (currentPage < 1)	currentPage = 1; //왼쪽 화살표 누르면 1페이제에서 0페이지로 가는데 0페이지는 존재하지 않으니까 1페이지에 머물게 하는 로직
		//전달된 현재 페이지가 마지막 페이지보다 크면 현재페이지를 마지막 페이지로 지정
		if (currentPage > totalPageCount)	currentPage = totalPageCount; //오른쪽 화살표눌러서 9페이지로 갈라하지만 9페이지는 존재하지 않으므로 8페이지에 머물게한다.
		
		this.currentPage = currentPage; 

		//현재 그룹 --내가보고있는 1이라는 페이지가 0번그룹 1번그룹 어디에 속해있는지 ex) 1~5까지는 0번그룹, 6~8은 1번그룹
		currentGroup = (currentPage - 1) / pagePerGroup;
		
		//현재 그룹의 첫 페이지
		startPageGroup = currentGroup * pagePerGroup + 1; //0번그룹의 첫번째 페이지는 1, 1번그룹의 첫번쨰 페이즈는 6  
		//현재 그룹의 마지막 페이지
		endPageGroup = startPageGroup + pagePerGroup - 1; //0번그룹의 마지막 페이지는 5  
		//현재 그룹의 마지막 페이지가 전체 페이지 수보다 크면 마지막 페이지를 전체 페이지 수로 지정
		endPageGroup = endPageGroup > totalPageCount ? totalPageCount : endPageGroup; //1번그룹의 마지막페이지는 10이어야하는데 8페이지까지밖에 게시물이 존재하지 않으므로 총페이지를 마지막페이지로 지정
		//전체 레코드 중 현재 페이지 첫 글의 위치(RowBounds 전달용)
		startRecord = (currentPage - 1) * countPerPage; //??
	}
}
