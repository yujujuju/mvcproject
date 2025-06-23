package com.example.mvcproject.vo;

/**
 * 페이징 VO
 */
public class PagingSearchVO {

    private int page = 1; //현재페이지
    private int pageSize = 10; //한 페이지 당 게시물 수
    private int startRow;        // DB 쿼리에 넘길 시작 행 번호
    private int endRow;          // DB 쿼리에 넘길 끝 행 번호

    private int totalRecord;      // 전체 데이터 수 (DB에서 조회)
    private int totalPage;        // 전체 페이지 수


    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
        this.totalPage = (int) Math.ceil((double) totalRecord / pageSize);

        this.startRow = (page - 1) * pageSize + 1;
        this.endRow = page * pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }


    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
