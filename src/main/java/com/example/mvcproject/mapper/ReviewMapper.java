package com.example.mvcproject.mapper;

import com.example.mvcproject.vo.ReviewVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 리뷰 mapper
 */
@Mapper
public interface ReviewMapper {

    /**
     * 리뷰 조회
     * @param bookId
     * @return
     */
    List<ReviewVO> selectReviewByBookId(int bookId);

    /**
     * 리뷰 등록
     * @param review
     * @return
     */
    int insertReview(ReviewVO review);
}
