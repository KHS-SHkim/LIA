package com.project.LIA.controller;

import com.project.LIA.domain.UserDomain;
import com.project.LIA.repository.AuthorityRepository;
import com.project.LIA.repository.UserRepository;
import com.project.LIA.service.BookService;
import com.project.LIA.util.U;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class HomeController {

    AuthorityRepository authorityRepository;

    UserRepository userRepository;

    @Autowired
    BookService bookService;


    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setAuthorityRepository(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    public HomeController(){}

    @RequestMapping("/")
    public String home(){
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String home(@RequestParam(required = false) String username,@RequestParam(required = false) String cate, @RequestParam(required = false) String keyword, Integer page, Model model) {


        UserDomain user = U.getLoggedUser();
        if (user != null){

            user = userRepository.findById(user.getId()).orElse(null);
            model.addAttribute("profile_img", user.getProfile_img());
            System.out.println("log12" + user.getProfile_img());
        } else {
            model.addAttribute("profile_img", null);
        }

        if (cate != null && keyword==null) {
            model.addAttribute("list", bookService.cateList(cate, page, model));
        } else if(cate == null && keyword!=null) {
            model.addAttribute("list", bookService.searchList(keyword,page, model));
        }
        else{
            model.addAttribute("list", bookService.list(page, model));
        }
        if(username!=null)
        {
            model.addAttribute("list", bookService.myList(username, page, model));

        }
        return "/home";
    }

    @PostMapping("/pageRows")
    public String pageRows(Integer page, Integer pageRows){
        U.getSession().setAttribute("pageRows", pageRows);
        return "redirect:/book/list?page=" + page;
    }

    // 로그인한 정보
    @RequestMapping("/auth")
    @ResponseBody
    public Authentication auth(){
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
