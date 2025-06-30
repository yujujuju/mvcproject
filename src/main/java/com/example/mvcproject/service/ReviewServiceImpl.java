package com.example.mvcproject.service;

import com.example.mvcproject.mapper.BookMapper;
import com.example.mvcproject.mapper.ReviewMapper;
import com.example.mvcproject.vo.BookVO;
import com.example.mvcproject.vo.ReviewLikeVO;
import com.example.mvcproject.vo.ReviewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 리뷰 serviceImpl
 */
@Service
public class ReviewServiceImpl {

   private final ReviewMapper reviewMapper;
   private final BookMapper bookMapper;

   @Autowired
   public ReviewServiceImpl(ReviewMapper reviewMapper, BookMapper bookMapper) {
       this.reviewMapper = reviewMapper;
       this.bookMapper = bookMapper;
   }

    /**
     * 리뷰 등록
     * @param review
     * @return
     */
   public ReviewVO insertReview(ReviewVO review) {

       BookVO book = bookMapper.selectBookById(review.getBookId());
       if (book == null) {
           throw new IllegalArgumentException("리뷰 등록 실패: 해당 도서가 존재하지 않습니다. [bookId=" + review.getBookId() + "]");
       }

       if(review.getContent() == null || review.getContent().trim().isEmpty())  {
           throw new IllegalArgumentException("리뷰 내용은 비워둘 수 없습니다.");
       }

       if(review.getRating() < 1 || review.getRating() > 5)  {
           throw new IllegalArgumentException("별점은 1점 이상 5점 이하로 입력해야 합니다.");
       }

       int result = reviewMapper.insertReview(review);
       if (result == 0){
           throw new RuntimeException("리뷰 등록에 실패했습니다.");
       }

       return review;
   }

    /**
     * 리뷰 조회
     * @param param
     * @return
     */
   public List<ReviewVO> getReviewByBookId(Map<String, Object> param) {
       return reviewMapper.selectReviewByBookId(param);
   }

    /**
     * 리뷰 수정
     * @param review
     * @return
     */
   public int updateReview(ReviewVO review) {
       return reviewMapper.updateReview(review);
   }

    /**
     * 리뷰 삭제
     * @param reviewId
     * @return
     */
   public int deleteReview(int reviewId,String userId) {
       return reviewMapper.deleteReview(reviewId,userId);
   }

    /**
     * 리뷰 좋아요
     * @param userId
     * @param reviewId
     * @return
     */
   public int addReviewLike(String userId, int reviewId) {

       ReviewLikeVO like = new ReviewLikeVO();
       like.setUserId(userId);
       like.setReviewId(reviewId);

       // 사용자가 해당 리뷰에 좋아요 눌렀는지 여부 확인 (0 = 안누름, 1 = 누름)
       if (reviewMapper.isReviewLikedByUser(like) == 0) {
           // 안눌렀으면 좋아요 등록
           return reviewMapper.insertReviewLike(like);
       }
       // 이미 누름
       return 0;

   }

    /**
     * 좋아요 여부 확인
     * @param like
     * @return
     */
    public boolean isReviewLikedByUser(ReviewLikeVO like) {
        return reviewMapper.isReviewLikedByUser(like) > 0;
    }

    /**
     * 좋아요 삭제
     * @param like
     * @return
     */
    public int removeReviewLike(ReviewLikeVO like) {
        return reviewMapper.deleteReviewLike(like);
    }




}
