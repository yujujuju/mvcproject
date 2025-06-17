package com.example.mvcproject.mapper;

import com.example.mvcproject.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 사용자 매퍼
 */
@Mapper
public interface UserMapper {

    /* 회원가입 */
    int insertUser(UserVO user);

    /* 아이디 중복체크 */
    int countByUserId(String userId);

    /* 아이디 조회 */
    UserVO selectUserById(String userId);
}
