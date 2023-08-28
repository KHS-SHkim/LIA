package com.project.LIA.controller;

import com.project.LIA.domain.UserDomain;
import com.project.LIA.repository.AuthorityRepository;
import com.project.LIA.repository.UserRepository;
import com.project.LIA.util.U;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class HomeController {

    AuthorityRepository authorityRepository;

    UserRepository userRepository;

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
    public String home(Model model ){

        UserDomain user = U.getLoggedUser();
        System.out.println("log12345");
        if (user != null){

            user = userRepository.findById(user.getId()).orElse(null);
            model.addAttribute("profile_img", user.getProfile_img());
            System.out.println("log12" + user.getProfile_img());
        } else
            System.out.println("로그인 22222 log1]2`");{
            model.addAttribute("profile_img", null);
        }
        return "/home";
    }

    // 로그인한 정보
    @RequestMapping("/auth")
    @ResponseBody
    public Authentication auth(){
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
