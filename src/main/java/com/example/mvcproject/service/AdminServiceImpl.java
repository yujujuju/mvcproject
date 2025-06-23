package com.example.mvcproject.service;

import com.example.mvcproject.mapper.AdminMapper;
import com.example.mvcproject.mapper.UserMapper;
import com.example.mvcproject.vo.BookRequestVO;
import com.example.mvcproject.vo.BookVO;
import com.example.mvcproject.vo.PagingSearchVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * 관리자 서비스
 */
@Service
public class AdminServiceImpl {

    private final AdminMapper adminMapper;

    @Autowired
    public AdminServiceImpl(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    /**
     * 요청 도서 목록
     * @return
     */
    public List<BookRequestVO> getAllBookRequests(PagingSearchVO paging) {
        return adminMapper.selectAllBookRequests(paging);
    }

    /**
     * 요청 도서 목록 카운트
     * @param
     * @return
     */
    public int getBookRequestCount() {
        return adminMapper.getBookRequestCount();
    }

    /**
     * 요청 도서 상세
     * @param requestId
     * @return
     */
    public BookRequestVO getBookRequestById(int requestId) {
        return adminMapper.selectBookRequestById(requestId);
    }

    /**
     * 요청 도서 승인
     * @param requestId
     * @return
     */
    public void approveBookRequest(int requestId) {
       adminMapper.approveBookRequest(requestId);
    }

}
