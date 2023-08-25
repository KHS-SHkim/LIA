package com.project.LIA.controller;

import com.project.LIA.domain.BookDomain;
import com.project.LIA.domain.BookValidator;
import com.project.LIA.service.BookService;
import com.project.LIA.util.U;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    public BookController(){
        System.out.println(getClass().getName() + "() 생성");
    }

    // 글 작성 이동
    @GetMapping("/write")
    public void write(){}

    //글 작성
    @PostMapping("/write")
    public String writeOk(
            @RequestParam Map<String, MultipartFile> files,   // 첨부파일들
            @ModelAttribute("book")
            @Valid // @Valid : binding 시 Validator 객체가 검증수행케 함.
            BookDomain book
            , BindingResult result  // Validator 가 유효성 검사를 수행한 결과가 담긴 객체
            , Model model
            , RedirectAttributes redirectAttrs    // redirect 시 넘겨줄 값들
    ){
        // validation 과정에서 에러가 있었다면 redirect 할거다!
        if(result.hasErrors()){
            // redirect 시 기좀에 입력했던 값들은 보이게 하기
            redirectAttrs.addFlashAttribute("user", book.getUser());
            redirectAttrs.addFlashAttribute("name", book.getName());
            redirectAttrs.addFlashAttribute("cate", book.getCate());
            redirectAttrs.addFlashAttribute("price", book.getPrice());
            redirectAttrs.addFlashAttribute("rental_spot", book.getRental_spot());
            redirectAttrs.addFlashAttribute("rental_stat", book.getRental_stat());
            redirectAttrs.addFlashAttribute("rental_date", book.getRental_date());



            List<FieldError> errList = result.getFieldErrors();
            for(FieldError err : errList){
                System.out.println(err.getField() + " : " + err.getCode());
                redirectAttrs.addFlashAttribute("error_" + err.getField(), err.getCode());
            }

            return "redirect:/board/write";   // GET
        }


        int write = bookService.write(book, files);
        model.addAttribute("result", write);

        return "book/writeOk";
    }

    // 상세 글
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable int id, Model model){
        model.addAttribute("post", bookService.selectById(id));
        return "board/detail";
    }

    // 전체 글 리스트
    @GetMapping("/list")
    //public void list(Model model){
    public void list(Integer page, Model model){
        model.addAttribute("list", bookService.list(page, model));
    }

    //카테고리별 리스트
    @GetMapping("/list/{cate}")
    public void cateList(String cate,Integer page, Model model){
        model.addAttribute("list", bookService.cateList(cate,page, model));
    }

    //검색 리스트
    @GetMapping("/list/{keyword}")
    public void searchList(String keyword,Integer page, Model model){
        model.addAttribute("list", bookService.searchList(keyword,page, model));
    }

    // 페이징
    // pageRows 변경시 동작
    @PostMapping("/pageRows")
    public String pageRows(Integer page, Integer pageRows){
        U.getSession().setAttribute("pageRows", pageRows);
        return "redirect:/board/list?page=" + page;
    }

    //수정창 이동
    @GetMapping("/update/{id}")
    public String update(@PathVariable int id, Model model){
        model.addAttribute("book", bookService.selectById(id));
        return "book/update";
    }

    //사진 포함 수정
    @PostMapping("/update")
    public String updateOk(
            @Valid BookDomain book
            , @RequestParam Map<String, MultipartFile> files   // 새로 추가될 첨부파일들
            , Long[] delfile   // 삭제될 파일들
            , BindingResult result
            , Model model  // 매개변수 선언시 BindingResult 보다 Model 을 뒤에 두어야 한다.
            , RedirectAttributes redirectAttrs
    ){
        // validation 과정에서 에러가 있었다면 redirect 할거다!
        if(result.hasErrors()){
            // redirect 시 기좀에 입력했던 값들은 보이게 하기
            redirectAttrs.addFlashAttribute("user", book.getUser());
            redirectAttrs.addFlashAttribute("name", book.getName());
            redirectAttrs.addFlashAttribute("cate", book.getCate());
            redirectAttrs.addFlashAttribute("price", book.getPrice());
            redirectAttrs.addFlashAttribute("rental_spot", book.getRental_spot());
            redirectAttrs.addFlashAttribute("rental_stat", book.getRental_stat());
            redirectAttrs.addFlashAttribute("rental_date", book.getRental_date());

            List<FieldError> errList = result.getFieldErrors();
            for(FieldError err : errList){
                System.out.println(err.getField() + " : " + err.getCode());
                redirectAttrs.addFlashAttribute("error_" + err.getField(), err.getCode());
            }

            return "redirect:/book/update/" + book.getId();   // GET
        }


        model.addAttribute("result", bookService.update(book, files, delfile));
        return "book/updateOk";
    }

    //삭제
    @PostMapping("/delete")
    public String deleteOk(int id, Model model){
        model.addAttribute("result", bookService.deleteById(id)); //삭제 성공시 1 리턴
        return "book/deleteOk";
    }

    @InitBinder  // 이 컨트롤러 클래스의 handler 에서 폼 데이터를 바인딩 할때 검증하는 Validator 객체 지정
    public void initBinder(WebDataBinder binder){
        System.out.println("initBinder() 호출");
        binder.setValidator(new BookValidator());
    }


}
