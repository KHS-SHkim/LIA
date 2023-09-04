package com.project.LIA.controller;

import com.project.LIA.domain.BookDomain;
import com.project.LIA.domain.DeclarationDomain;
import com.project.LIA.domain.UserDomain;
import com.project.LIA.repository.BookRepository;
import com.project.LIA.repository.DeclarationRepository;
import com.project.LIA.service.BookService;
import com.project.LIA.service.DeclarationService;
import com.project.LIA.service.UserService;
import com.project.LIA.util.U;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/declaration")
public class DeclarationController {

    @Autowired
    DeclarationService declarationService;

    @Autowired
    BookService bookService;

    @Autowired
    UserService userService;


    @GetMapping("/list")
    public String callList(Model model){
        List<DeclarationDomain> declarations = declarationService.findDeclarations();
        model.addAttribute("declarations", declarations);
        return "/declaration/list";
    }

    @GetMapping("/write")
    public String declarationWrite(Model model, Long book_id){
        UserDomain user = U.getLoggedUser();
        BookDomain book = bookService.selectById(book_id);
        UserDomain reporter = userService.findById(book.getUser().getId());

        DeclarationDomain declaration = DeclarationDomain.builder()
                .user(user)
                .reporter(reporter)
                .book(book)
                .build();
        model.addAttribute("user", user);
        model.addAttribute("reporter", reporter);
        model.addAttribute("declaration", declaration);
        model.addAttribute("book", book);
        return "/declaration/write";
    }

    @PostMapping("/write")
    public String declarationWrite(
            Model model,
            @RequestParam("user_id") String user_id,
            @RequestParam("reporter_id") String reporter_id,
            @RequestParam("book_id") String book_id,
            @RequestParam("reportType") String reportType,
            @RequestParam(value = "reportTypeOrder", required = false) String reportTypeOrder,
            @RequestParam("reportContent") String reportContent){
        String rt;

        if (reportType.equals("기타")){
            rt = reportTypeOrder;
        } else {
            rt = reportType;
        }
        DeclarationDomain declaration = DeclarationDomain.builder()
                .user(userService.findById(Long.parseLong(user_id)))
                .reporter(userService.findById(Long.parseLong(reporter_id)))
                .book(bookService.selectById(Long.parseLong(book_id)))
                .reportType(rt)
                .reportContent(reportContent)
                .build();
//        System.out.println("declaration :::::: " + declaration);
        declarationService.write(declaration);

        if (declaration.getId() == null ){
            model.addAttribute("state", "Fail");
        } else {
            model.addAttribute("state", "Success");
        }
        return "/declaration/writeOk";
    }

    @GetMapping("/detail/{declaration_id}")
    public String declarationDetail (@PathVariable("declaration_id")String declaration_id, Model model){
        DeclarationDomain declaration = declarationService.findDeclarationDetail(Long.parseLong(declaration_id));
        UserDomain user = declaration.getUser();
        UserDomain reporter = declaration.getReporter();
        BookDomain book = declaration.getBook();

        model.addAttribute("declaration", declaration);
        model.addAttribute("user", user);
        model.addAttribute("reporter", reporter);
        model.addAttribute("book", book);

        return "/declaration/detail";
    }

    @PostMapping("/answer")
    public String declarationAnswer (@RequestParam("declaration_id") String declaration_id, @RequestParam("answerContent") String answerContent, Model model){
        DeclarationDomain declaration = declarationService.findDeclarationDetail(Long.parseLong(declaration_id));
        declaration.setAnswerContent(answerContent);
        declaration.setAnswerDate(LocalDateTime.now());
        declarationService.write(declaration);
        model.addAttribute("declaration", declaration);
        return "/declaration/answer";
    }

}
