package com.example.mvcproject.web;

import com.example.mvcproject.service.ReviewServiceImpl;
import com.example.mvcproject.vo.ReviewVO;
import com.example.mvcproject.vo.UserVO;
import oracle.jdbc.proxy.annotation.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
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
     * @param session
     * @param rttr
     * @return
     */
    @PostMapping("/write")
    public String insertReview(
            @RequestParam("bookId") int bookId,@RequestParam("content") String content, @RequestParam("rating") String ratingStr,
            HttpSession session, RedirectAttributes rttr) {

        // 로그인 체크
        UserVO loginUser = (UserVO) session.getAttribute("loginUser");
        if (loginUser == null) {
            // 로그인 안 되었을 시 로그인 페이지로 리다이렉트
            rttr.addFlashAttribute("error", "로그인이 필요합니다.");
            return "redirect:/user/login?error=loginRequired";
        }

        // 리뷰 객체 생성
        ReviewVO review = new ReviewVO();
        review.setBookId(bookId); // 도서 id set
        review.setContent(content); // 리뷰 내용 set

        // 별점 파싱 및 예외처리
        try {
            review.setRating(Double.parseDouble(ratingStr)); // 문자열로 받은 별점 double로 변환
        } catch (NumberFormatException e) {
            // 숫자 아닌 경우 예외처리
            rttr.addFlashAttribute("error", "별점 형식이 잘못되었습니다.");
            return "redirect:/book/detail/" + bookId;
        }

        // 로그인 한 사용자면 id set
        review.setUserId(loginUser.getUserId());

        // 리뷰 insert
        try {
            reviewService.insertReview(review);
            rttr.addFlashAttribute("success", "리뷰가 등록되었습니다.");
        } catch (Exception e) {
            rttr.addFlashAttribute("error", "리뷰 등록에 실패하였습니다." + e.getMessage());
        }

        // 도서 상세 페이지로 리다이렉트
        return "redirect:/book/detail/" + bookId;
    }

    /**
     * 리뷰 수정
     * @param reviewId
     * @param content
     * @param session
     * @param rttr
     * @return
     */
    @PostMapping("/update")
    public String updateReview(@RequestParam("reviewId") int reviewId,
                               @RequestParam("content") String content,
                               @RequestParam("bookId") int bookId,
                               HttpSession session,
                               RedirectAttributes rttr) {

        // 로그인 여부 확인
        UserVO loginUser = (UserVO) session.getAttribute("loginUser");
        if (loginUser == null) {
            rttr.addFlashAttribute("error", "로그인이 필요합니다.");
            return "redirect:/user/login?error=loginRequired";
        }

        // 리뷰 정보 세팅
        ReviewVO review = new ReviewVO();
        review.setReviewId(reviewId);
        review.setContent(content);
        review.setUserId(loginUser.getUserId());
        review.setBookId(bookId);

        // 수정
        int result = reviewService.updateReview(review);
        if (result > 0) {
            rttr.addFlashAttribute("success", "리뷰가 수정되었습니다.");
        } else {
            rttr.addFlashAttribute("error", "리뷰 수정에 실패하였습니다.");
        }

        return "redirect:/book/detail/" + bookId;
    }

    /**
     * 댓글 삭제
     * @param reviewId
     * @param bookId
     * @param session
     * @param rttr
     * @return
     */
    @PostMapping("/delete")
    public String deleteReview(@RequestParam("reviewId") int reviewId, @RequestParam("bookId") int bookId,
                               HttpSession session, RedirectAttributes rttr) {

        // 로그인 체크
        UserVO loginUser = (UserVO) session.getAttribute("loginUser");
        if (loginUser == null) {
            rttr.addFlashAttribute("error", "로그인이 필요합니다.");
            return "redirect:/user/login?error=loginRequired";
        }

        // 삭제 요청
        int result = reviewService.deleteReview(reviewId, loginUser.getUserId());

        if (result > 0) {
            rttr.addFlashAttribute("success", "리뷰가 삭제되었습니다.");
        } else {
            rttr.addFlashAttribute("error", "리뷰 삭제에 실패하였습니다.");
        }

        return "redirect:/book/detail/" + bookId;
    }

}
