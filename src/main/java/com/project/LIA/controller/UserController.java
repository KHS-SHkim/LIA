package com.project.LIA.controller;

import com.project.LIA.domain.AddressDomain;
import com.project.LIA.domain.UserDomain;
import com.project.LIA.domain.UserValidator;
import com.project.LIA.domain.UserVofR;
import com.project.LIA.service.AddressService;
import com.project.LIA.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
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

    @PostMapping("/loginError")
    public String loginError(){
        return "user/loginError";
    }

    @GetMapping("/callback")
    public @ResponseBody String kakaoCallBack(String code){

        // POST 방식으로 key=value 데터를 요청(카카오쪽으로)
        RestTemplate rt = new RestTemplate();

        // HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpBody 오브젝트 생성
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("grant_type","authorization_code");
        params.add("client_id","0d1c222166732e07ce216a638ad66db8");
        params.add("redirect_uri","http://localhost:8094/user/callback");
        params.add("code",code);

        // HttpHeader 와 HttpBody 를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String,String>> kakaoTokenRequest = new HttpEntity<>(params,headers);

        // Http 요청하기 -Post방식으로 - 그리고 response 변수의 응답 받음.
        ResponseEntity<String> response = rt.exchange(
          "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        return "카카오 인증 완료 코드 값:"+ code + "\n카카오 토큰요청에 대한 응답:" + response;
    }

    @GetMapping("/register")
    public void register(Model model){

    }

    @PostMapping("/register")
    public String register(
                           @RequestParam("inputAuthNum") String inputAuthNum,
                           @RequestParam("authNum") String authNum,
                           @Valid UserVofR userVofR,
                           BindingResult result,
                           Model model,
                           RedirectAttributes redirectAttributes
    ){
        if (!result.hasFieldErrors("username") && userService.isExist(userVofR.getUsername())) {
            result.rejectValue("username", "이미존재하는 아이디 입니다.");
        }

        if(result.hasErrors()){
            redirectAttributes.addFlashAttribute("username", userVofR.getUsername());
            redirectAttributes.addFlashAttribute("nickname", userVofR.getNickname());
            redirectAttributes.addFlashAttribute("phone", userVofR.getPhone());
            redirectAttributes.addFlashAttribute("email", userVofR.getEmail());
            redirectAttributes.addFlashAttribute("post_num", userVofR.getPost_num());
            redirectAttributes.addFlashAttribute("address", userVofR.getAddress());
            redirectAttributes.addFlashAttribute("address_detail", userVofR.getAddress_detail());
            redirectAttributes.addFlashAttribute("inputAuthNum",inputAuthNum);
            redirectAttributes.addFlashAttribute("authNum", authNum);

            List<FieldError> errorList = result.getFieldErrors();
            for(FieldError err : errorList){
                redirectAttributes.addFlashAttribute("error_"+err.getField(), err.getCode());
            }
            return "redirect:/user/register";
        }

        UserDomain userDomain = new UserDomain();
        userDomain.setUsername(userVofR.getUsername());
        userDomain.setPassword(userVofR.getPassword());
        userDomain.setNickname(userVofR.getNickname());
        userDomain.setPhone(userVofR.getPhone());
        userDomain.setEmail(userVofR.getEmail());

        int cnt = userService.register(userDomain);

        AddressDomain addressDomain = new AddressDomain();
        addressDomain.setUser(userDomain);
        addressDomain.setAddress_detail(userVofR.getAddress_detail());
        addressDomain.setAddress(userVofR.getAddress());
        addressDomain.setPost_num(userVofR.getPost_num());

        addressService.register(addressDomain);

        model.addAttribute("result", cnt);

        return "/user/registerOk";
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

    @GetMapping("/registerOk")
    public void registerOk(){}



    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
        webDataBinder.setValidator(new UserValidator());
    }

}
