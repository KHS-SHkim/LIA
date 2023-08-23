package com.project.LIA.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class HomeController {

    public HomeController(){}

    @RequestMapping("/")
    public String home(){
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public void home(Model model){}

    // 로그인한 정보
    @RequestMapping("/auth")
    @ResponseBody
    public Authentication auth(){
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
