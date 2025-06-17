package com.example.mvcproject.web;

import com.example.mvcproject.service.UserServiceImpl;
import com.example.mvcproject.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 사용자 컨트롤러
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;


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
    public String signup(@ModelAttribute UserVO user) {
        System.out.println("userVO = " + user);
        userService.insertUser(user);
        return "redirect:/user/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }


}
