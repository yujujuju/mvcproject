package com.example.mvcproject.vo;

import java.util.Date;

/**
 * 리뷰 VO
 */
public class ReviewVO {

    private int reviewId; // 리뷰 ID(PK)
    private int bookId; // 도서 ID(FK)
    private String userId; // 로그인 아이디
    private String nickname;    // 닉네임
    private Double  rating; // 별점
    private String content; // 리뷰 내용
    private Date createDate; // 작성일

    public ReviewVO(int reviewId, int bookId, String userId, String nickname, Double rating, String content, Date createDate) {
        this.reviewId = reviewId;
        this.bookId = bookId;
        this.userId = userId;
        this.nickname = nickname;
        this.rating = rating;
        this.content = content;
        this.createDate = createDate;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
