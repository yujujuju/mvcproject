package com.example.mvcproject.web;

import com.example.mvcproject.service.UserServiceImpl;
import com.example.mvcproject.vo.BookRequestVO;
import com.example.mvcproject.vo.BookVO;
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

    /**
     * 도서 요청 화면
     * @param model
     * @return
     */
    @GetMapping("/requestBook")
    public String requestBookForm(Model model, @SessionAttribute("loginUser")UserVO loginUser) {

        //도서 요청 횟수
        int todayCount = userService.countTodayRequest(loginUser.getUserId());

        model.addAttribute("requestBook", new BookVO());
        model.addAttribute("todayCount", todayCount);
        return "mypage/user/bookRequest";
    }

    /**
     * 도서 요청
     * @param requestBook
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/requestBook")
    public String submitBookRequest(@ModelAttribute("requestBook") BookRequestVO requestBook, @SessionAttribute("loginUser") UserVO loginUser, RedirectAttributes redirectAttributes){

        //사용자 ID 세팅
        requestBook.setUserId(loginUser.getUserId());

        //도서 요청 횟수 제한(하루 3번)
        int todayCount = userService.countTodayRequest(loginUser.getUserId());

        if (todayCount >= 3) {
            redirectAttributes.addFlashAttribute("msg", "도서 요청은 하루 3회까지만 가능합니다.");
            return "redirect:/user/requestBook";
        }

        //도서 요청 insert
        int result = userService.saveBookRequest(requestBook);

        if (result > 0) {
            redirectAttributes.addFlashAttribute("msg", "도서 요청이 등록되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("msg", "요청 처리 중 오류가 발생했습니다.");
        }
        return "redirect:/user/requestBook";
    }

    /**
     * 도서 요청 현황
     * @param model
     * @param loginUser
     * @return
     */
    @GetMapping("/requestList")
    public String requestList(Model model, @SessionAttribute("loginUser")UserVO loginUser) {


        List<BookRequestVO> requestList = userService.getBookRequestList(loginUser.getUserId());
        model.addAttribute("requestList", requestList);

        System.out.println("요청 리스트 수: " + requestList.size());


        return "mypage/user/bookRequestList";

    }

}
