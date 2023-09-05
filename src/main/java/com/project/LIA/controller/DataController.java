package com.project.LIA.controller;


import com.project.LIA.domain.DeclarationDomain;
import com.project.LIA.domain.NoteAjaxData;
import com.project.LIA.domain.UserDomain;
import com.project.LIA.service.DeclarationService;
import com.project.LIA.service.NoteService;
import com.project.LIA.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController   // data 를 response 한다
public class DataController {

    @Autowired
    NoteService noteService;

    @Autowired
    UserService userService;

    @Autowired
    DeclarationService declarationService;


    @GetMapping("/note/detailAjax")
    public NoteAjaxData detailAjax(Model model, @RequestParam(name = "user_id") Long user_id, @RequestParam(name = "receiver_id") Long receiver_id){

        UserDomain user = userService.findById(user_id);
        UserDomain receiver = userService.findById(receiver_id);
        return noteService.findNoteAjax(user, receiver);
    }

    @PostMapping("/admin/chDeclaration")
    public List<DeclarationDomain> declarationAjax(Model model, @RequestParam(name="state") String state){
        List<DeclarationDomain> list = declarationService.findAnswerNoAnswer(state);
        for ( DeclarationDomain d : list){
            d.setUserNickname(d.getUser().getNickname());
            d.setReceiverNickname(d.getReporter().getNickname());
            d.setBookInfo(d.getBook().getId());
            d.setBookName(d.getBook().getName());
            System.out.println(d);
        }
        return list;
    }

}
