package com.example.mvcproject.web;

import com.example.mvcproject.service.AdminServiceImpl;
import com.example.mvcproject.service.BookServiceImpl;
import com.example.mvcproject.service.ReviewServiceImpl;
import com.example.mvcproject.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 관리자 컨트롤러
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminServiceImpl adminService;

    @Autowired
    private BookServiceImpl boardService;

    @Autowired
    public AdminController(AdminServiceImpl adminService) {
        this.adminService = adminService;
    }

    @Autowired
    private ServletContext context;

    /**
     * 세션 및 권한 체크 공통 메서드
     */
    private boolean isAdmin(HttpSession session) {
        UserVO loginUser = (UserVO) session.getAttribute("loginUser");
        return loginUser != null && "ADMIN".equalsIgnoreCase(loginUser.getRole());
    }

    /**
     * 도서 목록
     * @return
     */
    @GetMapping("/book/list")
    public String list(Model model, @RequestParam(defaultValue = "1") int page, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/user/login";

        int pageSize = 10;
        int totalCount = boardService.getBookCount();
        PagingSearchVO paging = new PagingSearchVO();
        paging.setPage(page);
        paging.setPageSize(pageSize);
        paging.setTotalRecord(totalCount);

        List<BookVO> bookList = boardService.getAllBooks(paging);
        model.addAttribute("bookList", bookList);
        model.addAttribute("paging", paging);
        return "mypage/admin/bookList";
    }

    /**
     * 도서 등록 화면
     * @return
     */
    @GetMapping("/book/register")
    public String BookRegister(Model model, @ModelAttribute("preFillBook") BookRequestVO preFillBook, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/user/login";

        BookVO book = new BookVO();
        if (preFillBook != null && preFillBook.getTitle() != null) {
            book.setTitle(preFillBook.getTitle());
            book.setAuthor(preFillBook.getAuthor());
            book.setPublisher(preFillBook.getPublisher());
        }
        model.addAttribute("book", book);
        model.addAttribute("mode", "create");
        model.addAttribute("formAction", "/admin/book/register");
        return "mypage/admin/bookRegister";
    }

    /**
     * 도서 등록
     * @param book
     * @param imageFile
     * @return
     * @throws IOException
     */
    @PostMapping("/book/register")
    public String BookRegister(@ModelAttribute BookVO book, BindingResult result,
                               @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                               HttpSession session) throws IOException {
        if (!isAdmin(session)) return "redirect:/user/login";

        if (imageFile != null && !imageFile.isEmpty()) {
            String uploadDir = context.getRealPath("/upload/book");
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            File savedFile = new File(dir, fileName);
            imageFile.transferTo(savedFile);
            book.setImagePath("/upload/book/" + fileName);
        } else if (book.getThumbnailLink() != null && !book.getThumbnailLink().isEmpty()) {
            book.setImagePath(book.getThumbnailLink());
        }

        if (result.hasErrors()) {
            System.out.println("에러: " + result.getAllErrors());
            return "mypage/admin/bookRegister";
        }

        boardService.insertBook(book);
        return "redirect:/admin/book/list";
    }

    /**
     * 도서 수정 화면
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/book/edit/{id}")
    public String BookEdit(@PathVariable int id, Model model, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/user/login";

        BookVO book = boardService.getBookById(id);
        model.addAttribute("book", book);
        model.addAttribute("mode", "edit");
        model.addAttribute("formAction", "/admin/book/update");
        return "mypage/admin/bookRegister";
    }


    /**
     * 도서 수정
     * @param book
     * @param imageFile
     * @return
     * @throws IOException
     */
    @PostMapping("/book/update")
    public String updateBook(@ModelAttribute BookVO book, @RequestParam(value = "imageFile", required = false) MultipartFile imageFile, HttpSession session) throws IOException {
        if (!isAdmin(session)) return "redirect:/user/login";

        if (imageFile != null && !imageFile.isEmpty()) {
            String uploadDir = context.getRealPath("/upload/book");
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            File savedFile = new File(dir, fileName);
            imageFile.transferTo(savedFile);
            book.setImagePath("/upload/book/" + fileName);
        }

        boardService.updateBook(book);
        return "redirect:/admin/book/list";
    }

    /**
     * 도서 상세보기
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/book/detail/{id}")
    public String BookDetail(@PathVariable("id") int id, Model model, HttpSession session) {

        if (!isAdmin(session)) return "redirect:/user/login";

        BookVO book = boardService.getBookById(id);
        model.addAttribute("book", book);
        return "mypage/admin/bookDetail";
    }

    /**
     * 도서 삭제
     * @param id
     * @return
     */
    @PostMapping("/book/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteBook(@PathVariable("id") int id) {
        try {
            boardService.deleteBook(id);
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("fail");
        }
    }

    /**
     * 도서 요청 목록
     * @param model
     * @return
     */
    @GetMapping("/book/requestList")
    public String requestBookList(Model model, @RequestParam(defaultValue = "1") int page, HttpSession session) {

        if (!isAdmin(session)) return "redirect:/user/login";

        int pageSize = 10;
        int totalCount = adminService.getBookRequestCount();
        PagingSearchVO paging = new PagingSearchVO();
        paging.setPage(page);
        paging.setPageSize(pageSize);
        paging.setTotalRecord(totalCount);

        List<BookRequestVO> requestList = adminService.getAllBookRequests(paging);
        model.addAttribute("requestList", requestList);

        return "mypage/admin/bookRequestList";
    }

    /**
     * 요청 도서 상세
     * @param requestId
     * @param model
     * @return
     */
    @GetMapping("/book/requestDetail/{id}")
    public String requestDetail(@PathVariable("id") int requestId, Model model,HttpSession session) {

        if (!isAdmin(session)) return "redirect:/user/login";

        //요청 id로 요청 정보 조회
        BookRequestVO request = adminService.getBookRequestById(requestId);
        model.addAttribute("request", request);
        return "mypage/admin/bookRequestDetail";
    }

    /**
     * 요청 도서 승인
     * @param requestId
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/book/approve")
    public String approveRequest(@RequestParam("requestId") int requestId, RedirectAttributes redirectAttributes) {

        //요청 상태 '승인'으로 업데이트
        adminService.approveBookRequest(requestId);

        //요청 id로 요청 정보 조회
        BookRequestVO request = adminService.getBookRequestById(requestId);

        //도서 등록 화면으로 리다이렉트 + 요청 데이터 전달
        redirectAttributes.addFlashAttribute("preFillBook", request);
        return "redirect:/admin/book/register";
    }

    /**
     * 요청 도서 거절
     * @param requestId
     * @param rejectReason
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/book/reject")
    public String rejectRequest(@RequestParam("requestId")int requestId, @RequestParam("rejectReason") String rejectReason, RedirectAttributes redirectAttributes) {

        //요청 상태 '거절'로 업데이트
        adminService.rejectBookRequest(requestId, rejectReason);

        // 알림 메세지 전달
        redirectAttributes.addFlashAttribute("msg","도서 요청이 거절되었습니다.");

        return "redirect:/admin/book/requestList";
    }

    /**
     * 회원 목록 조회
     * @param keyword
     * @param model
     * @return
     */
    @GetMapping("/user/list")
    public String userList(@RequestParam(defaultValue = "1")int page,@RequestParam(required = false) String keyword, @RequestParam(defaultValue = "nickname") String searchType, Model model,HttpSession session) {

        if (!isAdmin(session)) return "redirect:/user/login";

        UserVO user = new UserVO();
        user.setPage(page);
        user.setPageSize(10);
        user.setKeyword(keyword);
        user.setSearchType(searchType);

        int totalCount = adminService.getUserCount(user);
        user.setTotalRecord(totalCount);

        //유저 목록 조회
        List<UserVO> userList = adminService.getUserList(user);

        model.addAttribute("userList", userList);
        model.addAttribute("searchName", keyword);
        model.addAttribute("paging", user);
        model.addAttribute("searchType",searchType);
        return "mypage/admin/userList";
    }

    /**
     * 도서 검색 팝업(카카오 책 API)
     * @param query
     * @param model
     * @return
     */
    @GetMapping("/bookSearch")
    public String BookSearch(@RequestParam(required = false) String query, Model model,HttpSession session){

        if (!isAdmin(session)) return "redirect:/user/login";

        model.addAttribute("query", query);
        return "mypage/admin/bookSearch";
    }

    /**
     * 사용자 리뷰 관리(조회)
     * @param page
     * @param model
     * @return
     */
    @GetMapping("review-manage")
    public String reviewManage(@RequestParam(defaultValue = "1")int page, Model model,HttpSession session) {

        if (!isAdmin(session)) return "redirect:/user/login";

        int totalCount = adminService.getTotalReviewCount();

        ReviewVO review = new ReviewVO();
        review.setPage(page);
        review.setPageSize(10);
        review.setTotalRecord(totalCount);

        // 리뷰 목록 조회
        List<ReviewVO> reviewList = adminService.getAdminReviewList(review);

        model.addAttribute("reviewList", reviewList);
        model.addAttribute("paging", review);

        return "mypage/admin/reviewManage";

    }

    /**
     * 사용자 리뷰 삭제
     * @param reviewId
     * @return
     */
    @PostMapping(value = "review-manage", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String deleteReview(@RequestParam("reviewId") int reviewId) {
        int result = adminService.deleteReview(reviewId);
        return String.valueOf(result);
    }



}
