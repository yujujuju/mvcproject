package com.example.mvcproject.web;

import com.example.mvcproject.service.BookServiceImpl;
import com.example.mvcproject.service.ReviewServiceImpl;
import com.example.mvcproject.vo.BookVO;
import com.example.mvcproject.vo.PagingSearchVO;
import com.example.mvcproject.vo.ReviewVO;
import com.example.mvcproject.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 도서 컨트롤러
 */
@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookServiceImpl bookService;

    @Autowired
    private ReviewServiceImpl reviewService;

    @Autowired
    public BookController(BookServiceImpl boardService) {
        this.bookService = boardService;
    }

    /**
     * 도서 목록
     * @return
     */
    @GetMapping("/bookList")
    public String bookList(@ModelAttribute BookVO book, Model model) {

        int pageSize = 18;
        book.setPageSize(pageSize);

        int totalCount;
        List<BookVO> bookList;

        // 키워드 있는 경우 → 검색
        if (book.getKeyword() != null && !book.getKeyword().trim().isEmpty()) {
            totalCount = bookService.getSearchBookCount(book);
            book.setTotalRecord(totalCount);

            bookList = bookService.searchBooksByTitle(book);
            model.addAttribute("keyword", book.getKeyword()); // input 유지용
        } else {
            totalCount = bookService.getBookCount();
            book.setTotalRecord(totalCount);

            bookList = bookService.getAllBooks(book);
        }

        model.addAttribute("bookList", bookList);
        model.addAttribute("paging", book);

        return "book/bookList";
    }



    /**
     * 도서 상세보기 (도서정보,리뷰)
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/detail/{id}")
    public String BookDetail(@PathVariable("id") int id, HttpSession session, Model model) {

        // 도서 정보
        BookVO book = bookService.getBookById(id);
        model.addAttribute("book", book);

        // 로그인 사용자 ID 가져오기
        UserVO loginUser = (UserVO) session.getAttribute("loginUser");
        String loginUserId = (loginUser != null) ? loginUser.getUserId() : null;

        // 리뷰 조회 파라미터 구성
        Map<String, Object> param = new HashMap<>();
        param.put("bookId", id); // <- 여기 bookId였는데 id로 수정해야 함
        param.put("loginUserId", loginUserId); // 로그인 안 되어 있으면 null

        // 리뷰 목록 조회
        List<ReviewVO> reviewList = reviewService.getReviewByBookId(param);
        model.addAttribute("reviewList", reviewList);

        return "book/bookDetail";
    }


}
