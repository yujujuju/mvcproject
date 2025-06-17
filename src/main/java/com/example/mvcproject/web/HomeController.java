package com.example.mvcproject.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @GetMapping("/")
    public String root() {
        return "redirect:/main";
    }

    /**
     * 메인 화면
     * @return
     */
    @GetMapping("/main")
    public String home() {
        return "home";
    }

    /**
     * 마이페이지
     * @param session
     * @return
     */
    @GetMapping("/mypage")
    public String mypage(HttpSession session) {
        Object loginUser = session.getAttribute("loginUser");
        //(object)로 받는 이유 : 어떤 타입으로 넣었든 꺼낼때 오류 안나려고

        if (loginUser == null) {
            return "redirect:/user/login"; //로그인 안했을 시
        }

        return "mypage"; //로그인 했으면 마이페이지 진입
    }
}
