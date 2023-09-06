package com.project.LIA.controller;

import com.project.LIA.domain.AddressDomain;
import com.project.LIA.domain.UserDomain;
import com.project.LIA.service.AddressService;
import com.project.LIA.service.AdminService;
import com.project.LIA.service.DeclarationService;
import com.project.LIA.service.BookService;
import com.project.LIA.service.UserService;
import com.project.LIA.util.U;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @Autowired
    UserService userService;

    @Autowired
    AddressService addressService;

    @Autowired
    DeclarationService declarationService;


    @Autowired
    BookService bookService;




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
    public void userList(Integer page,Model model){
        List<UserDomain> userList = adminService.userList(page,model);
        if(userList != null){
            model.addAttribute("userList",userList);
        } else{
            model.addAttribute("userList",null);
        }
        UserDomain userDomain = U.getLoggedUser();
        model.addAttribute("profile_img",userDomain.getProfile_img());
    }

    @PostMapping("/chState")
    public @ResponseBody int changeState(@RequestParam("state")String state,
                                         @RequestParam("username")String username
    ){
        System.out.println("----------------------------"+ state + "------------");
        System.out.println("----------------------------"+ username + "------------");

        UserDomain userDomain = userService.findByUsername(username);

        userDomain.setState(state);
        userService.updateSt(userDomain);

        return 1;
    }

    @GetMapping("/userDetail/{username}")
    public String changePassword(@PathVariable String username, Model model){

        UserDomain userDomain = userService.findByUsername(username);
        model.addAttribute("user",userDomain);

        AddressDomain addressDomain = addressService.findByUserId(userDomain.getId());
        String address = addressDomain.getAddress();
        String post_num = addressDomain.getPost_num();
        String address_detail = addressDomain.getAddress_detail();

        model.addAttribute("profile_img",U.getLoggedUser().getProfile_img());
        model.addAttribute("post_num",post_num);
        model.addAttribute("address",address);
        model.addAttribute("address_detail",address_detail);

        return "/admin/userDetail";
    }

    @GetMapping("/declarationList")
    public String declarationList(Model model){
        model.addAttribute("profile_img",U.getLoggedUser().getProfile_img());
        model.addAttribute("list",declarationService.findAnswerNoAnswer(""));
        return "/admin/declarationList";
    }
    @GetMapping("/adminList")
    public void list(Integer page, Model model) {
        model.addAttribute("profile_img",U.getLoggedUser().getProfile_img());
        model.addAttribute("list", bookService.list(page, model));
    }


    @PostMapping("/delete")
    public String deleteOk(long id, Model model){
        model.addAttribute("result", bookService.deleteById(id)); //삭제 성공시 1 리턴
        return "admin/deleteOk";
    }




}
