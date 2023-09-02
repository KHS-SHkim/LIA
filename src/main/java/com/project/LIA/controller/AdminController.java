package com.project.LIA.controller;

import com.project.LIA.domain.UserDomain;
import com.project.LIA.service.AdminService;
import com.project.LIA.service.UserService;
import com.project.LIA.util.U;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @Autowired
    UserService userService;



    public AdminController(){
        System.out.println(getClass().getName() + "()생성");
    }
    @GetMapping("/main")
    public void main(Model model){
        UserDomain userDomain = U.getLoggedUser();
        if (userDomain != null){

            userDomain = userService.findById(userDomain.getId());
            model.addAttribute("profile_img", userDomain.getProfile_img());
        } else {
            model.addAttribute("profile_img", null);
        }
    }

    @GetMapping("/userList")
    public void userList(Model model){
        List<UserDomain> userList = adminService.userList();
        if(userList != null){
            model.addAttribute("userList",userList);
        } else{
            model.addAttribute("userList",null);
        }
    }



}
