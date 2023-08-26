package com.project.LIA.controller;

import com.project.LIA.domain.AddressDomain;
import com.project.LIA.domain.UserDomain;
import com.project.LIA.domain.UserValidator;
import com.project.LIA.domain.UserVofR;
import com.project.LIA.service.AddressService;
import com.project.LIA.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    public UserController(){
        System.out.println(getClass().getName() + "() 생성");
    }

    @GetMapping("/login")
    public void login(){}

    @GetMapping("/register")
    public void register(){}

    @PostMapping("/register")
    public String register(
                           @Valid @ModelAttribute UserVofR userVofR,
                           @RequestParam("inputAuthNum") String inputAuthNum,
                           @RequestParam("authNum") String authNum,
                           BindingResult result,
                           Model model,
                           RedirectAttributes redirectAttributes
    ){
        System.out.println("userVofR" + userVofR.toString());

        if(!result.hasFieldErrors("username") && userService.isExist(userVofR.getUsername())){
            result.rejectValue("username","이미 존재하는 아이디 입니다.");
        }

        if(!result.hasFieldErrors("email") && userService.isEmail(userVofR.getEmail())){
            result.rejectValue("email", "이미 존재하는 이메일 입니다.");
        }

        if(result.hasErrors()){
            redirectAttributes.addFlashAttribute("username", userVofR.getUsername());
            redirectAttributes.addFlashAttribute("nickname", userVofR.getNickname());
            redirectAttributes.addFlashAttribute("phone", userVofR.getPhone());
            redirectAttributes.addFlashAttribute("email", userVofR.getEmail());
            redirectAttributes.addFlashAttribute("post_num", userVofR.getPost_num() );
            redirectAttributes.addFlashAttribute("address", userVofR.getAddress());
            redirectAttributes.addFlashAttribute("address_detail", userVofR.getAddress_detail());
            redirectAttributes.addFlashAttribute("inputAuthNum", inputAuthNum);
            redirectAttributes.addFlashAttribute("authNum", authNum);

            List<FieldError> errorList = result.getFieldErrors();
            for(FieldError error : errorList){
                redirectAttributes.addFlashAttribute("error_" + error.getField(), error.getCode());
                break;
            }
            return "redirect:/user/register";
        }

        UserDomain userDomain = new UserDomain();
        userDomain.setUsername(userVofR.getUsername());
        userDomain.setPassword(userVofR.getPassword());
        userDomain.setNickname(userVofR.getNickname());
        userDomain.setPhone(userVofR.getPhone());
        userDomain.setEmail(userVofR.getEmail());

        AddressDomain addressDomain = new AddressDomain();
        addressDomain.setAddress(userVofR.getAddress());
        addressDomain.setAddress_detail(userVofR.getAddress_detail());
        addressDomain.setPost_num(userVofR.getPost_num());

        int cnt = userService.register(userDomain);
        addressDomain.setUser(userDomain);
        addressService.register(addressDomain);
        model.addAttribute("result", cnt);

        return "/user/registerOK";
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

    @InitBinder
    public void initBinder(WebDataBinder binder){
//        binder.setValidator(new UserValidator());
    }

}
