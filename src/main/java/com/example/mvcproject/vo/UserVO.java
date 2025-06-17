package com.example.mvcproject.vo;

/**
 * 사용자 VO
 */
public class UserVO {

    private int id;              // PK
    private String userId;       // 로그인 아이디
    private String password;    // 비밀번호
    private String nickname;    // 닉네임
    private String createdAt;   // 등록일자
    private String role; // 권한(USER or ADMIN)


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
}
