package com.example.mvcproject.web;

import com.example.mvcproject.service.BoardServiceImpl;
import com.example.mvcproject.vo.BookVO;
import com.example.mvcproject.vo.PagingSearchVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 도서 컨트롤러
 */
@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BoardServiceImpl boardService;

    @Autowired
    public BookController(BoardServiceImpl boardService) {
        this.boardService = boardService;
    }

    /**
     * 도서 목록
     * @return
     */
    @GetMapping("/bookList")
    public String Booklist(Model model, @RequestParam(defaultValue = "1")int page) {
        int pageSize = 10;

        // 전체 게시글 수 조회
        int totalCount = boardService.getBookCount();

        // 페이징 VO 생성
        PagingSearchVO paging = new PagingSearchVO();
        paging.setPage(page);
        paging.setPageSize(pageSize);
        paging.setTotalRecord(totalCount);

        // 도서 목록 조회
        List<BookVO> bookList = boardService.getAllBooks(paging);
        model.addAttribute("bookList", bookList);
        model.addAttribute("paging", paging);
        return "book/bookList";
    }

    /**
     * 도서 상세보기
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/detail/{id}")
    public String BookDetail(@PathVariable("id") int id, Model model) {
        BookVO book = boardService.getBookById(id);
        model.addAttribute("book", book);
        return "book/bookDetail";
    }

}
