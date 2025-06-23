package com.example.mvcproject.service;

import com.example.mvcproject.mapper.UserMapper;
import com.example.mvcproject.vo.BookRequestVO;
import com.example.mvcproject.vo.BookVO;
import com.example.mvcproject.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class UserServiceImpl {

    private final UserMapper userMapper;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 회원가입
     * @param user
     * @return
     */
    public int insertUser(UserVO user) {

        //비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        return userMapper.insertUser(user);
    }

    /**
     * 아이디 중복체크
     * @param userId
     * @return
     */
    public boolean isUserIdDuplicate(String userId) {
        return userMapper.countByUserId(userId) > 0;
    }

    /**
     * 아이디 조회(로그인)
     * @param userId
     * @return
     */
    public UserVO getUserById(String userId) {
        return userMapper.selectUserById(userId);
    }

    /**
     * 도서 요청
     * @param requestBook
     * @return
     */
    public int saveBookRequest(BookRequestVO requestBook) {
        return userMapper.insertBookRequest(requestBook);
    }

    /**
     * 도서 요청 횟수 제한
     * @param userId
     * @return
     */
    public int countTodayRequest(String userId) {
        return userMapper.countTodayRequest(userId);
    }

    /**
     * 도서 요청 현황
     * @param userId
     * @return
     */
    public List<BookRequestVO> getBookRequestList(String userId) {
        return userMapper.selectRequestList(userId);
    }


}
