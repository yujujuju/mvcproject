package com.example.mvcproject.web;

import com.example.mvcproject.service.ReviewServiceImpl;
import com.example.mvcproject.vo.ReviewLikeVO;
import com.example.mvcproject.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * 댓글 좋아요 Controller
 */
@Controller
@RequestMapping("/review-like")
public class ReviewLikeController {

    @Autowired
    private ReviewServiceImpl reviewService;

    /**
     * 리뷰 좋아요
     * @param reviewId
     * @param session
     * @return
     */
    @PostMapping("/{reviewId}")
    public ResponseEntity<String> likeReview(@PathVariable int reviewId, HttpSession session) {

        // 세션에서 로그인 사용자 꺼냄
        UserVO loginUser = (UserVO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("unauthorized");
        }

        String userId = loginUser.getUserId();

        // VO 객체에 userId + reviewId 세팅
        ReviewLikeVO like = new ReviewLikeVO();
        like.setUserId(userId);
        like.setReviewId(reviewId);

        // 이미 좋아요 눌렀는지 확인
        boolean alreadyLiked = reviewService.isReviewLikedByUser(like);

        if (alreadyLiked) {
            // 눌렀으면 삭제
            reviewService.removeReviewLike(like);
            return ResponseEntity.ok("unliked");
        } else {
            // 안 눌렀으면 추가
            reviewService.addReviewLike(userId, reviewId);
            return ResponseEntity.ok("liked");
        }
    }

}