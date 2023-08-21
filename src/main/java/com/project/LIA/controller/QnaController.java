package com.project.LIA.controller;

import com.project.LIA.domain.QnADomain;
import com.project.LIA.service.QnaService;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public class QnaController {
    @Autowired
    private QnaService qnaService;

    public QnaController() {
        System.out.println(getClass().getName() + "()생성");
    }

    @GetMapping("/list")
    public void list(Integer page, Model model){

//        model.addAttribute("List", QnaService.list(page, model));

    }
}
