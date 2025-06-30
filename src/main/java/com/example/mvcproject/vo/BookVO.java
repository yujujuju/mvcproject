package com.example.mvcproject.vo;

import java.util.Date;

/**
 * 도서 VO
 */
public class BookVO extends PagingSearchVO {

    private int bookId;            // 도서 고유 ID (PK)
    private String title;          // 도서 제목
    private String author;         // 저자
    private String publisher;      // 출판사
    private Date pubDate;        // 출판일
    private String description;    // 줄거리 / 내용
    private String imagePath;      // 썸네일 이미지 경로
    private Double avgRating; // 평균 별점

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Double avgRating) {
        this.avgRating = avgRating;
    }
}
