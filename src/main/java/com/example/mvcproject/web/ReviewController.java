package com.example.mvcproject.web;

import com.example.mvcproject.service.ReviewServiceImpl;
import com.example.mvcproject.vo.ReviewVO;
import com.example.mvcproject.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewServiceImpl reviewService;

    @Autowired
    public ReviewController(ReviewServiceImpl reviewService) {
        this.reviewService = reviewService;
    }

    /**
     * 리뷰 등록
     * @param review
     * @param session
     * @param rttr
     * @return
     */
    @PostMapping("/write")
    public String insertReview(@ModelAttribute ReviewVO review, HttpSession session, RedirectAttributes rttr) {

        // id 가져오기
        UserVO loginUser = (UserVO) session.getAttribute("loginUser");

            // 로그인 안했을 시 등록x
            if (loginUser == null) {
                rttr.addFlashAttribute("error", "로그인이 필요합니다.");
                return "redirect:/user/login?error=loginRequired";
            }

        // id 세팅
        review.setUserId(loginUser.getUserId());

            try {
                // 댓글 insert
                reviewService.insertReview(review);
                rttr.addFlashAttribute("success", "리뷰가 등록되었습니다.");
            } catch (Exception e) {
                rttr.addFlashAttribute("error", "리뷰 등록에 실패하였습니다."+ e.getMessage());
            }

        System.out.println(">>> rating = " + review.getRating());
        System.out.println(">>> bookId = " + review.getBookId());
        System.out.println(">>> content = " + review.getContent());

        // 도서 상세 페이지
        return "redirect:/book/detail/" + review.getBookId();

    }

}
