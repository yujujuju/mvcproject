package com.example.mvcproject.web;

import com.example.mvcproject.service.AdminServiceImpl;
import com.example.mvcproject.service.BoardServiceImpl;
import com.example.mvcproject.vo.BookRequestVO;
import com.example.mvcproject.vo.BookVO;
import com.example.mvcproject.vo.PagingSearchVO;
import com.example.mvcproject.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
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
    private BoardServiceImpl boardService;

    @Autowired
    public AdminController(AdminServiceImpl adminService) {
        this.adminService = adminService;
    }

    @Autowired
    private ServletContext context;

    /**
     * 도서 목록
     * @return
     */
    @GetMapping("/book/list")
    public String list(Model model, @RequestParam(defaultValue = "1")int page) {
        int pageSize = 10;

        // 전체 게시글 수 조회
        int totalCount = boardService.getBookCount();

        // 페이징 VO 생성
        PagingSearchVO paging = new PagingSearchVO();
        paging.setPage(page);
        paging.setPageSize(pageSize);
        paging.setTotalRecord(totalCount);

        // 도서 목록 조회
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
    public String BookRegister(Model model,@ModelAttribute("preFillBook") BookRequestVO preFillBook) {
        BookVO book = new BookVO();

        // 요청도서 데이터 넘어온거 있으면 새로운 book 객체에 set해서 바인딩
        if (preFillBook != null && preFillBook.getTitle() != null) {
            book.setTitle(preFillBook.getTitle());
            book.setAuthor(preFillBook.getAuthor());
            book.setPublisher(preFillBook.getPublisher());
        }

        // 바인딩한 값 뷰로 전달
        // priFillBook 값이 null이면 새로 등록
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
    public String BookRegister(@ModelAttribute BookVO book,   @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) throws IOException {

        // 파일 비었는지 체크
        if (!imageFile.isEmpty()) {
            String uploadDir = context.getRealPath("/upload/book"); // 서버 내부 경로(저장위치)
            // 디렉토리 존재 확인, 없으면 생성
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs(); {
                // 파일 이름 겹치지 않게 업로드 시간 기반으로 파일명 생성
                String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
                File savedFile = new File(dir, fileName);
                imageFile.transferTo(savedFile); // 서버 디스크에 저장
                // 디비에 저장할 이미지 웹 경로
                book.setImagePath("/upload/book/" + fileName);
            }

        }

        // 도서 정보 insert
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
    public String BookEdit(@PathVariable int id, Model model) {
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
    public String updateBook(@ModelAttribute BookVO book, @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) throws IOException {

        // 이미지 새로 업로드된 경우만 처리
        if (!imageFile.isEmpty()) {
            // 저장 디렉토리 설정
            String uploadDir = context.getRealPath("/upload/book");
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            // 파일명 중복 방지를 위한 타임스탬프
            String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            File savedFile = new File(dir, fileName);
            imageFile.transferTo(savedFile);

            // 이미지 경로 BookVO에 세팅
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
    public String BookDetail(@PathVariable("id") int id, Model model) {
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
    public String requestBookList(Model model, @RequestParam(defaultValue = "1") int page) {

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
    public String requestDetail(@PathVariable("id") int requestId, Model model) {

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
    public String userList(@RequestParam(defaultValue = "1")int page,@RequestParam(required = false) String keyword, @RequestParam(defaultValue = "nickname") String searchType, Model model) {

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



}
