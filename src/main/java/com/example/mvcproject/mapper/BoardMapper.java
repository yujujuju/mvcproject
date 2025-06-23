package com.example.mvcproject.mapper;

import com.example.mvcproject.vo.BookVO;
import com.example.mvcproject.vo.PagingSearchVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 도서 mapper
 */
@Mapper
public interface BoardMapper {

    /**
     * 도서 목록 조회
     * @return
     */
    List<BookVO> selectAllBooks(PagingSearchVO page);

    /**
     * 도서 단건 조회
     * @param bookId
     * @return
     */
    BookVO selectBookById(int bookId);

    /**
     * 도서 개수 조회
     * @return
     */
    int getBookCount();

    /**
     * 도서 등록
     * @param book
     * @return
     */
    int insertBook(BookVO book);

    /**
     * 도서 수정
     * @param book
     * @return
     */
    int updateBook(BookVO book);

    /**
     * 도서 삭제
     * @param bookId
     * @return
     */
    int deleteBook(int bookId);

    /**
     * 최신 등록 도서 조회
     * @return
     */
    List<BookVO> selectRecentBooks();
}
