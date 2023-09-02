package com.project.LIA.controller;


import com.project.LIA.domain.NoteAjaxData;
import com.project.LIA.domain.UserDomain;
import com.project.LIA.service.NoteService;
import com.project.LIA.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController   // data 를 response 한다
@RequestMapping("/note")
public class NoteDataController {

    @Autowired
    NoteService noteService;

    @Autowired
    UserService userService;

    public NoteDataController(){
        System.out.println(getClass().getName() + "() 생성");
    }

    @GetMapping("/detailAjax")
    public NoteAjaxData detailAjax(Model model, @RequestParam(name = "user_id") Long user_id, @RequestParam(name = "receiver_id") Long receiver_id){

        UserDomain user = userService.findById(user_id);
        UserDomain receiver = userService.findById(receiver_id);

        return noteService.findNoteAjax(user, receiver);
    }
}
