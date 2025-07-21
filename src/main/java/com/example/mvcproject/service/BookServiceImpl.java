package com.example.mvcproject.service;

import com.example.mvcproject.mapper.BookMapper;
import com.example.mvcproject.vo.BookVO;
import com.example.mvcproject.vo.PagingSearchVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class BookServiceImpl {

    private final BookMapper bookMapper;

    @Autowired
    public BookServiceImpl(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    /**
     * 도서 단건 조회
     * @param bookId
     * @return
     */
    public BookVO getBookById(int bookId) {
        return bookMapper.selectBookById(bookId);
    }

    /**
     * 도서 목록 조회
     * @return
     */
    public List<BookVO> getAllBooks(PagingSearchVO page) {
        return bookMapper.selectAllBooks(page);
    }

    /**
     * 도서 개수 카운트
     * @return
     */
    public int getBookCount() {
        return bookMapper.getBookCount();
    }

    /**
     * 도서 등록
     * @param book
     * @return
     */
    public int insertBook(BookVO book) {
        return bookMapper.insertBook(book);
    }

    /**
     * 도서 수정
     * @param book
     * @return
     */
    public int updateBook(BookVO book) {
        return bookMapper.updateBook(book);
    }

    /**
     * 도서 삭제
     * @param id
     * @return
     */
    public int deleteBook(@PathVariable("id") int id) {
        return bookMapper.deleteBook(id);
    }

    /**
     * 최신 등록 도서 조회
     * @return
     */
    public List<BookVO> getRecentBooks() {
        return bookMapper.selectRecentBooks();
    }

    /**
     * 도서 리뷰 높은 순
     * @return
     */
    public List<BookVO> getTopBooksByAvgRating() {
        return bookMapper.selectTopBooksByAvgRating();
    }

    /**
     * 도서 검색
     * @param book
     * @return
     */
    public List<BookVO> searchBooksByTitle(BookVO book) {
        return bookMapper.searchBooksByTitle(book);
    }

    /**
     * 도서 검색 카운트
     * @param book
     * @return
     */
    public int getSearchBookCount(BookVO book) {
        return bookMapper.getSearchBookCount(book);
    }


}
