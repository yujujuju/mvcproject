package com.example.mvcproject.service;

import com.example.mvcproject.mapper.BoardMapper;
import com.example.mvcproject.mapper.ReviewMapper;
import com.example.mvcproject.vo.BookVO;
import com.example.mvcproject.vo.ReviewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 리뷰 serviceImpl
 */
@Service
public class ReviewServiceImpl {

   private final ReviewMapper reviewMapper;
   private final BoardMapper boardMapper;

   @Autowired
   public ReviewServiceImpl(ReviewMapper reviewMapper, BoardMapper boardMapper) {
       this.reviewMapper = reviewMapper;
       this.boardMapper = boardMapper;
   }

    /**
     * 리뷰 등록
     * @param review
     * @return
     */
   public ReviewVO insertReview(ReviewVO review) {

       BookVO book = boardMapper.selectBookById(review.getBookId());
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
     * @param bookId
     * @return
     */
   public List<ReviewVO> getReviewByBookId(int bookId) {
       return reviewMapper.selectReviewByBookId(bookId);
   }
}
