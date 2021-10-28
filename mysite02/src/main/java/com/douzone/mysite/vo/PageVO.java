package com.douzone.mysite.vo;

public class PageVO {
	private int currentPage; // 현재 페이지
	private int totalCount; // 총 게시글의 수 [ SELECT COUNT(*) FROM BOARD ]
//	private int pageLimit; // 한 페이지에 보여질 페이징 수 ex) 총 페이지가 13개면, 1 ~ 5 / 6 ~ 10
	private int maxPage; // 총 페이지 (1 ~ 50)
	private int startPage; // 시작 페이지 (1 11 21 31 41)
	private int endPage; // 끝 페이지 (10 20 30 40 50)
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getMaxPage() {
		return maxPage;
	}
	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	@Override
	public String toString() {
		return "PageVO [currentPage=" + currentPage + ", totalCount=" + totalCount + ", maxPage=" + maxPage
				+ ", startPage=" + startPage + ", endPage=" + endPage + "]";
	}
	
	
	
	
}
