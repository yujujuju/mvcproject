package com.example.mvcproject.service;

import com.example.mvcproject.mapper.UserMapper;
import com.example.mvcproject.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

}
