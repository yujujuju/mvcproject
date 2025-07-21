package com.example.mvcproject.web;

import com.example.mvcproject.service.ReviewServiceImpl;
import com.example.mvcproject.service.UserServiceImpl;
import com.example.mvcproject.vo.BookRequestVO;
import com.example.mvcproject.vo.BookVO;
import com.example.mvcproject.vo.ReviewVO;
import com.example.mvcproject.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 사용자 컨트롤러
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    /**
     * 회원가입 form
     * @return
     */
    @GetMapping("/signup")
    public String singupForm(){
        return "signup";
    }

    /**
     * 아이디 중복체크
     * @param userId
     * @return
     */
    @GetMapping("/checkUserId")
    @ResponseBody
    public String checkUserId(@RequestParam("userId") String userId) {
        boolean isDuplicate = userService.isUserIdDuplicate(userId);
        return isDuplicate ? "duplicate" : "available";
    }

    /**
     * 회원가입
     * @param user
     * @return
     */
    @PostMapping("/signup")
    public String signup(@ModelAttribute UserVO user, Model model) {

        try {
            // 유저로 권한 세팅
            user.setRole("USER");
            userService.insertUser(user);
            return "signupSuccess"; //가입 성공 페이지
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("msg", "회원가입 중 오류가 발생했습니다.: ");
            return "signup";  // 가입 실패 시 회원가입 화면
        }
    }


    /**
     * 로그인 화면
     * @return
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 로그인
     * @param user
     * @param session
     * @param model
     * @return
     */
    @PostMapping("/login")
    public String login(UserVO user, HttpSession session, Model model) {

        UserVO dbuser = userService.getUserById(user.getUserId());

        if (dbuser == null) {
            model.addAttribute("msg", "존재하지 않는 아이디 입니다.");
            return "login";
        }

        if (!passwordEncoder.matches(user.getPassword(), dbuser.getPassword())) {
            model.addAttribute("msg", "비밀번호가 일치하지 않습니다.");
            return "login";
        }

        //최근 접속일 세팅
        userService.updateLastLogin(user.getUserId());
        dbuser = userService.getUserById(user.getUserId());
        session.setAttribute("loginUser", dbuser);
        return "redirect:/main";
    }

    /**
     * 로그아웃
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }


}
