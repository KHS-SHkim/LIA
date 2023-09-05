package com.project.LIA.controller;

import com.project.LIA.domain.QnADomain;
import com.project.LIA.domain.QnAValidator;
import com.project.LIA.domain.UserDomain;
import com.project.LIA.service.QnAService;
import com.project.LIA.service.UserService;
import com.project.LIA.util.U;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/qna")
public class QnAController {

    @Autowired
    QnAService qnAService;

    @Autowired
    UserService userService;

    public QnAController(){
        System.out.println(getClass().getName() + "()생성");
    }

    @GetMapping("/list")
    public void qnaList(Integer page, Model model){

        UserDomain userDomain = U.getLoggedUser();
        if(userDomain != null){
            userDomain = userService.findByUsername(userDomain.getUsername());

            if(userDomain != null){
                model.addAttribute("profile_img",userDomain.getProfile_img());
            }else{
                model.addAttribute("profile_img",null);
            }
        }

        List<QnADomain> qnaList = qnAService.getAllQnA(page,model);
        if(qnaList != null){
            model.addAttribute("qnaList",qnaList);
        }
    }

    @PostMapping("/searchList")
    public String searchList(@RequestParam(value="searchValue", required=false)String searchValue,Integer page, Model model){
        System.out.println(searchValue);
        UserDomain userDomain = U.getLoggedUser();
        if(userDomain != null){
            userDomain = userService.findByUsername(userDomain.getUsername());

            if(userDomain != null){
                model.addAttribute("profile_img",userDomain.getProfile_img());
            }else{
                model.addAttribute("profile_img",null);
            }
        }

        List<QnADomain> qnaList = qnAService.listBySearch(searchValue,page,model);
        model.addAttribute("qnaList",qnaList);
        model.addAttribute("searchValue",searchValue);
        return "/qna/searchList";
    }

    @GetMapping("/write")
    public void write(Model model){
        UserDomain userDomain = U.getLoggedUser();
        if(userDomain != null){
            userDomain = userService.findByUsername(userDomain.getUsername());
            model.addAttribute("username",userDomain.getUsername());
            model.addAttribute("nickname",userDomain.getNickname());
        }
    }

    @PostMapping("/write")
    public String writeOk(@ModelAttribute("qna")
                          @Valid QnADomain qna,
                          BindingResult result,
                          Model model,
                          RedirectAttributes redirectAttributes
    ){
        if(result.hasErrors()){
            redirectAttributes.addFlashAttribute("title",qna.getTitle());
            redirectAttributes.addFlashAttribute("question",qna.getQuestion());

            List<FieldError> errorList = result.getFieldErrors();
            for(FieldError err : errorList){
                redirectAttributes.addFlashAttribute("error_" + err.getField(), err.getCode());
            }
            return "redirect:/qna/write";
        }
        UserDomain userDomain = U.getLoggedUser();
        userDomain = userService.findByUsername(userDomain.getUsername());
        qna.setUser(userDomain);
        int cnt = qnAService.write(qna);

        model.addAttribute("result",cnt);
        return "/qna/writeOk";

    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model){

        UserDomain userDomain = U.getLoggedUser();
        if(userDomain != null){
            userDomain = userService.findByUsername(userDomain.getUsername());

            if(userDomain.getProfile_img() != null){
                model.addAttribute("profile_img",userDomain.getProfile_img());
            }else{
                model.addAttribute("profile_img",null);
            }
        }

        model.addAttribute("qna", qnAService.findById(id));

        return "/qna/detail";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") Long id, Model model){

        QnADomain qnADomain = qnAService.findById(id);

        int cnt = qnAService.delete(qnADomain);

        model.addAttribute("result",cnt);

        return "/qna/deleteOk";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model){

        UserDomain userDomain = U.getLoggedUser();
        if(userDomain != null){
            userDomain = userService.findByUsername(userDomain.getUsername());

            if(userDomain != null){
                model.addAttribute("profile_img",userDomain.getProfile_img());
            }else{
                model.addAttribute("profile_img",null);
            }
        }


        QnADomain qnADomain = qnAService.findById(id);

        model.addAttribute("qna",qnADomain);

        return "/qna/update";
    }

    @PostMapping("/update")
    public String update(@Valid QnADomain qna,
                         BindingResult result,
                         Model model,
                         RedirectAttributes redirectAttributes
    ){
        if(result.hasErrors()){
            redirectAttributes.addFlashAttribute("title",qna.getTitle());
            redirectAttributes.addFlashAttribute("question",qna.getQuestion());

            List<FieldError> errorList = result.getFieldErrors();
            for(FieldError err : errorList){
                redirectAttributes.addFlashAttribute("error_" + err.getField(), err.getCode());
            }
            return "redirect:/qna/update/" + qna.getId();
        }

        int cnt = qnAService.update(qna);

        model.addAttribute("result",cnt);
        model.addAttribute("qna",qna);

        return "/qna/updateOk";
    }

    @PostMapping("/answer")
    public String answer(QnADomain qnADomain,
                         Model model
    ){
        String answer = qnADomain.getAnswer();
        Long id = qnADomain.getId();

        qnADomain = qnAService.findById(id);

        if(qnADomain != null){
            qnADomain.setAnswer(answer);
            qnADomain.setAnswerDate(LocalDateTime.now());

            int cnt = qnAService.update(qnADomain);
            model.addAttribute("result",cnt);
            model.addAttribute("id",qnADomain.getId());
            return "/qna/answerOk";
        } else{
            model.addAttribute("result", 0);
            return "/qna/answerOk";
        }

    }

    @GetMapping("/list2")
    public void qnaList2(Integer page, Model model){

        UserDomain userDomain = U.getLoggedUser();
        if(userDomain != null){
            userDomain = userService.findByUsername(userDomain.getUsername());

            if(userDomain != null){
                model.addAttribute("profile_img",userDomain.getProfile_img());
            }else{
                model.addAttribute("profile_img",null);
            }
        }

        List<QnADomain> qnaList = qnAService.getAllQnA(page,model);
        if(qnaList != null){
            model.addAttribute("qnaList",qnaList);
        }
    }




    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.setValidator(new QnAValidator());
    }

}