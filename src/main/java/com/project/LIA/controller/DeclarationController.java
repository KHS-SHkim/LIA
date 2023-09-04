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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public void callList(Model model){
        model.addAttribute("declarations", declarationService.findDeclarations());

    }

    @ResponseBody
    @GetMapping("/write")
    public void declarationWrite(Model model, Long book_id){
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
    }

    @PostMapping("/write")
    public String declarationWrite(Model model, Long user_id, Long reporter_id, Long book_id, String reportType, String reportTypeOrder,String reportContent){
        String rt;
        if (reportType.equals("기타")){
            rt = reportTypeOrder;
        } else {
            rt = reportType;
        }


        DeclarationDomain declaration = DeclarationDomain.builder()
                .user(userService.findById(user_id))
                .reporter(userService.findById(reporter_id))
                .book(bookService.selectById(book_id))
                .reportType(rt)
                .reportContent(reportContent)
                .build();
        declarationService.write(declaration);

        return "writeOk";
    }

}
