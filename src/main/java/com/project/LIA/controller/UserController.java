package com.project.LIA.controller;

import com.project.LIA.domain.AddressDomain;
import com.project.LIA.domain.UserDomain;
import com.project.LIA.domain.UserValidator;
import com.project.LIA.domain.UserVofR;
import com.project.LIA.service.AddressService;
import com.project.LIA.service.UserService;
import com.project.LIA.util.U;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    @GetMapping("/findPassword")
    public void findPassWord(){}

    @PostMapping("/findPassword")
    public String findPasswordOk(UserVofR userVofR,
                                 Model model){
        System.out.println("비번변경시 받오는 값 확인: " + userVofR);

        UserDomain userDomain = userService.findByUsername(userVofR.getUsername());

        userDomain.setPassword(passwordEncoder.encode(userVofR.getPassword()));

        int cnt = userService.updatePw(userDomain);

        model.addAttribute("result", cnt);

        return "/user/findPasswordOk";
    }

    @PostMapping("/findPassword/check")
    public @ResponseBody int checkInfo(@RequestParam("username") String username,
                                       @RequestParam("phone") String phone,
                                       @RequestParam("email") String email
    ){
        System.out.println(username + "  :   " + phone + "    :   " + email );
        UserDomain userDomain = userService.findByUsername(username);
        String originUsername = userDomain.getUsername();
        String originPhone = userDomain.getPhone();
        String originEmail = userDomain.getEmail();
        System.out.println(originUsername + ",,,," + originPhone + ":::" + originEmail);

        if(originUsername.equals(username) && originPhone.equals(phone) && originEmail.equals(email)){
            return 0;
        } else{
            return 1;
        }

    }

    @GetMapping("/myPage")
    public void myPage(Model model){
        UserDomain userDomain = U.getLoggedUser();
        String username = userDomain.getUsername();

        userDomain = userService.findByUsername(username);

        AddressDomain addressDomain = addressService.findByUserId(U.getLoggedUser().getId());

        System.out.println("dddddddddddddddddddddd" + userDomain.getProfile_img());
        System.out.println("비밀번호 확인:" + userDomain.getPassword());


        model.addAttribute("profile_img", userDomain.getProfile_img());
        model.addAttribute("nickname", userDomain.getNickname());
        model.addAttribute("phone", userDomain.getPhone());
        model.addAttribute("post_num",addressDomain.getPost_num());
        model.addAttribute("address",addressDomain.getAddress());
        model.addAttribute("address_detail",addressDomain.getAddress_detail());
        model.addAttribute("username",userDomain.getUsername());
        model.addAttribute("email",userDomain.getEmail());
    }

    @PostMapping("/myPage")
    public String myPage(@RequestParam("upfile") MultipartFile multipartFile,
                         @RequestParam("originalImage") String originalImage,
                         UserVofR userVofR,
                         BindingResult result,
                         Model model,
                         RedirectAttributes redirectAttributes
    ){
        System.out.println("userVofR: =====" + userVofR.toString());

        UserDomain userDomain = userService.findByUsername(userVofR.getUsername());

        System.out.println("유저의 변경될 비밀번호 : " + userVofR.getPassword());


        if(userVofR.getProfile_img() != null){
            userDomain.setProfile_img(userVofR.getProfile_img());
        } else{
            userDomain.setProfile_img(null);
        }
        userDomain.setNickname(userVofR.getNickname());
        userDomain.setPhone(userVofR.getPhone());

        if(userVofR.getPassword() != null){
            System.out.println("패스워드 가 들어가있으면 동작해야하는 것!!!!");
            userDomain.setPassword(passwordEncoder.encode(userVofR.getPassword()));
        }

        int isDelete = 0;
        if(multipartFile == null) isDelete = 1;

        System.out.println("업데이트 직전의 UserDomain 정보 : " + userDomain);
        model.addAttribute("result", userService.update(isDelete, originalImage, userDomain, multipartFile));

        AddressDomain addressDomain = addressService.findByUserId(userDomain.getId());
        addressDomain.setPost_num(userVofR.getPost_num());
        addressDomain.setAddress(userVofR.getAddress());
        addressDomain.setAddress_detail(userVofR.getAddress_detail());

        addressService.update(addressDomain);

        return "/user/updateOk";




    }


    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
        webDataBinder.setValidator(new UserValidator());
    }

}
