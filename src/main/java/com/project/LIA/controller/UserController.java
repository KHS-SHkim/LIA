package com.project.LIA.controller;

import com.project.LIA.domain.UserDomain;
import com.project.LIA.service.UserService;
import com.project.LIA.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    public UserController(){
        System.out.println(getClass().getName() + "() 생성");
    }

    @GetMapping("/register")
    public void register(){}

    @PostMapping("/register")
    public String register(UserDomain user,
                           @RequestParam("inputAuthNum") String inputAuthNum,
                           @RequestParam("authNum") String authNum,
                           BindingResult result,
                           Model model,
                           RedirectAttributes redirectAttributes
    ){



        return "/registerOK";
    }

    @PostMapping("/register/usernameChk")
    public @ResponseBody int usernameChk(@RequestParam("username") String username){
        boolean b = userService.isExist(username);
        if(b) return 1;
        else{
            return 0;
        }
    }

    @PostMapping("/register/emailChk")
    public @ResponseBody int emailChk(@RequestParam("email") String email){
        boolean b = userService.isEmail(email);
        if(b) return 1;
        else{
            return 0;
        }
    }

}
