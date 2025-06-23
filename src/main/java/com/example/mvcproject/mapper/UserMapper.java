package com.example.mvcproject.mapper;

import com.example.mvcproject.vo.BookRequestVO;
import com.example.mvcproject.vo.BookVO;
import com.example.mvcproject.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 사용자 매퍼
 */
@Mapper
public interface UserMapper {

    /**
     * 회원가입
     * @param user
     * @return
     */
    int insertUser(UserVO user);

    /**
     * 아이디 중복체크
     * @param userId
     * @return
     */
    int countByUserId(String userId);

    /**
     * 아이디 조회
     * @param userId
     * @return
     */
    UserVO selectUserById(String userId);

    /**
     * 도서 요청
     * @param requestBook
     * @return
     */
    int insertBookRequest(BookRequestVO requestBook);

    /**
     * 도서 요청 횟수 제한
     * @param userId
     * @return
     */
    int countTodayRequest(String userId);

    /**
     * 도서 요청 현황
     * @param userId
     * @return
     */
    List<BookRequestVO> selectRequestList(String userId);
}
