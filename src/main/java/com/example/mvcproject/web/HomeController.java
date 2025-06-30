package com.example.mvcproject.web;

import com.example.mvcproject.service.BookServiceImpl;
import com.example.mvcproject.vo.BookVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
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

    /**
     * 마이페이지
     * @param session
     * @return
     */
    @GetMapping("/mypage")
    public String mypage(HttpSession session) {
        Object loginUser = session.getAttribute("loginUser");
        //(object)로 받는 이유 : 어떤 타입으로 넣었든 꺼낼때 오류 안나려고

        if (loginUser == null) {
            return "redirect:/user/login"; //로그인 안했을 시
        }

        return "mypage/mypage"; //로그인 했으면 마이페이지 진입
    }
}
