package com.example.mvcproject.mapper;

import com.example.mvcproject.vo.ReviewLikeVO;
import com.example.mvcproject.vo.ReviewVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 리뷰 mapper
 */
@Mapper
public interface ReviewMapper {

    /**
     * 리뷰 조회
     * @param param
     * @return
     */
    List<ReviewVO> selectReviewByBookId(Map<String, Object> param);

    /**
     * 리뷰 등록
     * @param review
     * @return
     */
    int insertReview(ReviewVO review);

    /**
     * 리뷰 수정
     * @param review
     * @return
     */
    int updateReview(ReviewVO review);

    /**
     * 리뷰 삭제
     * @param reviewId
     * @return
     */
    int deleteReview(@Param("reviewId") int reviewId, @Param("userId") String userId);

    /**
     * 리뷰 좋아요
     * @param like
     * @return
     */
    int insertReviewLike(ReviewLikeVO like);

    /**
     * 리뷰 좋아요 삭제
     * @param like
     * @return
     */
    int deleteReviewLike(ReviewLikeVO like);

    /**
     * 리뷰 좋아요 확인
     * @param like
     * @return
     */
    int isReviewLikedByUser(ReviewLikeVO like);

    /**
     * 리뷰 좋아요 카운트
     * @param reviewId
     * @return
     */
    int countReviewLikes(@Param("reviewId") int reviewId);

    /**
     * 리뷰 좋아요 삭제
     * @param like
     * @return
     */
    int removeReviewLike(ReviewLikeVO like);

    /**
     * 내 리뷰 조회
     * @param review
     * @return
     */
    List<ReviewVO> selectMyReview(ReviewVO review);

    /**
     * 내 리뷰 조회 카운트
     * @param userId
     * @return
     */
    int selectMyReviewCount(String userId);

}
