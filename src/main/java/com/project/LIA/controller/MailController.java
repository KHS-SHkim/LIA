package com.project.LIA.controller;

import com.project.LIA.domain.EmailChk;
import com.project.LIA.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MailController {

    @Autowired
    private EmailService emailService;

    @ResponseBody
    @PostMapping("/register/authEmail")
    public ResponseEntity<String> EmailCheck(@RequestBody EmailChk emailChk) throws Exception{
        return ResponseEntity.ok(emailService.sendEmail(emailChk.getEmail()));
    }
}
