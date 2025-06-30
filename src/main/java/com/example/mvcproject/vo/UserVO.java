package com.example.mvcproject.vo;
import java.sql.Timestamp;

/**
 * 사용자 VO
 */
public class UserVO extends PagingSearchVO{

    private int id;              // PK
    private String userId;       // 로그인 아이디
    private String password;    // 비밀번호
    private String nickname;    // 닉네임
    private String createdAt;   // 등록일자
    private String role; // 권한(USER or ADMIN)
    private Timestamp lastLogin; // 마지막 접속일자
    private String keyword;     // 검색어
    private String searchType;  // 검색 조건 (nickname or userId)

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Timestamp getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }
}
