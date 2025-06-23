package com.example.mvcproject.service;

import com.example.mvcproject.mapper.BoardMapper;
import com.example.mvcproject.vo.BookVO;
import com.example.mvcproject.vo.PagingSearchVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class BoardServiceImpl {

    private final BoardMapper boardMapper;

    @Autowired
    public BoardServiceImpl(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }

    /**
     * 도서 단건 조회
     * @param bookId
     * @return
     */
    public BookVO getBookById(int bookId) {
        return boardMapper.selectBookById(bookId);
    }

    /**
     * 도서 목록 조회
     * @return
     */
    public List<BookVO> getAllBooks(PagingSearchVO page) {
        return boardMapper.selectAllBooks(page);
    }

    /**
     * 도서 개수 카운트
     * @return
     */
    public int getBookCount() {
        return boardMapper.getBookCount();
    }

    /**
     * 도서 등록
     * @param book
     * @return
     */
    public int insertBook(BookVO book) {
        return boardMapper.insertBook(book);
    }

    /**
     * 도서 수정
     * @param book
     * @return
     */
    public int updateBook(BookVO book) {
        return boardMapper.updateBook(book);
    }

    /**
     * 도서 삭제
     * @param id
     * @return
     */
    public int deleteBook(@PathVariable("id") int id) {
        return boardMapper.deleteBook(id);
    }

    /**
     * 최신 등록 도서 조회
     * @return
     */
    public List<BookVO> getRecentBooks() {
        return boardMapper.selectRecentBooks();
    }


}
