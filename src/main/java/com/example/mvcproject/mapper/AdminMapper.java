package com.example.mvcproject.mapper;

import com.example.mvcproject.vo.BookRequestVO;
import com.example.mvcproject.vo.PagingSearchVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 관리자 mapper
 */
@Mapper
public interface AdminMapper {

    /**
     * 요청 도서 목록
     * @return
     */
    List<BookRequestVO> selectAllBookRequests(PagingSearchVO paging);

    /**
     * 요청 도서 목록 카운트
     * @return
     */
    int getBookRequestCount();

    /**
     * 요청 도서 상세
     * @param requestId
     * @return
     */
    BookRequestVO selectBookRequestById(int requestId);

    /**
     * 요청 도서 승인
     * @param requestId
     * @return
     */
    void approveBookRequest(int requestId);

}
