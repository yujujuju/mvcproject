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
    private int likeCount; // 좋아요 카운트
    private boolean likedByUser; // 좋아요 누른 유저

    public ReviewVO(int reviewId, int bookId, String userId, String nickname, Double rating, String content, Date createDate,int likeCount,boolean likedByUser) {
        this.reviewId = reviewId;
        this.bookId = bookId;
        this.userId = userId;
        this.nickname = nickname;
        this.rating = rating;
        this.content = content;
        this.createDate = createDate;
        this.likeCount = likeCount;
        this.likedByUser = likedByUser;
    }

    public ReviewVO() {
        // 기본 생성자
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

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public boolean isLikedByUser() {
        return likedByUser;
    }

    public void setLikedByUser(boolean likedByUser) {
        this.likedByUser = likedByUser;
    }
}
