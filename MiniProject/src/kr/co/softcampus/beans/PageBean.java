package kr.co.softcampus.beans;

public class PageBean {

	private int min;	//최소 페이지 번호
	
	private int max;	//최대 페이지 번호
	
	private int prevPage;	//이전 버튼의 페이지 번호
	
	private int nextPage;	//다음 버튼의 페이지 번호
	
	private int pageCnt;	//전체 페이지 개수
	
	private int currentPage;//현재 페이지 번호
	
	//contentCnt : 전체글 개수
	//currentPage : 현재 글 번호
	//contentPageCnt : 페이지당 글의 개수
	//paginationCnt	: 페이지 버튼의 개수
	public PageBean(int contentCnt, int currentPage, int contentPageCnt, int paginationCnt) {
		// TODO Auto-generated constructor stub
		
		this.currentPage = currentPage;//현재 페이지 번호
		pageCnt = contentCnt / contentPageCnt;	//전체 페이지 개수
		
		if (contentCnt % contentPageCnt > 0) {
			pageCnt++;
		}
		min = ((currentPage -1) / paginationCnt)*paginationCnt +1;
		max = min + paginationCnt -1;
		
		if (max > pageCnt) {
			max = pageCnt;
		}
		
		prevPage = min - 1;
		nextPage = max + 1;
		if (nextPage > pageCnt) {
			nextPage = pageCnt;
		}
		
	}

	public int getMin() {
		return min;
	}

	public int getMax() {
		return max;
	}

	public int getPrevPage() {
		return prevPage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public int getPageCnt() {
		return pageCnt;
	}

	public int getCurrentPage() {
		return currentPage;
	}
	
}
