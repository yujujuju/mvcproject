package com.example.mvcproject.web;

import com.example.mvcproject.service.BookServiceImpl;
import com.example.mvcproject.vo.BookVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;


/**
 * 메인 컨트롤러
 */
@Controller
public class HomeController {

    @Autowired
    private BookServiceImpl bookService;

    @GetMapping("/")
    public String root() {
        return "redirect:/main";
    }

    /**
     * 메인 화면
     * @return
     */
    @GetMapping("/main")
    public String home(Model model) {
        // 최근 등록 도서
        List<BookVO> recentBooks = bookService.getRecentBooks();
        model.addAttribute("recentBooks", recentBooks);

        // 베스트 리뷰 도서 (리뷰 높은 순)
        List<BookVO> topReviewBooks = bookService.getTopBooksByAvgRating();
        model.addAttribute("topReviewBooks", topReviewBooks);
        return "home";
    }

}
