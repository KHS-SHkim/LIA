package com.project.LIA.controller;

import com.project.LIA.repository.DeclarationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/declaration")
public class DeclarationController {

    @Autowired
    DeclarationRepository declarationRepository;

    @GetMapping("/list")
    public void callList(Model model){
        model.addAttribute("declarations", declarationRepository.findAll());

    }

}
