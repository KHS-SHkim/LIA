package com.project.LIA.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    public UserController(){
        System.out.println(getClass().getName() + "() 생성");
    }

    @GetMapping("/register")
    public void register(){}

}
