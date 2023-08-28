package com.project.LIA.controller;

import com.project.LIA.domain.NoteDomain;
import com.project.LIA.domain.UserDomain;
import com.project.LIA.service.NoteService;
import com.project.LIA.service.UserService;
import com.project.LIA.util.U;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/note")
public class NoteController {

    @Autowired
    NoteService noteService;

    @Autowired
    UserService userService;

    @GetMapping("/list")
    public String noteList(Model model){
//        UserDomain user = U.getLoggedUser();
        UserDomain user = userService.findByUsername("user1");
        System.out.println("user = " + user);
        model.addAttribute("myNoteList", noteService.findMyNoteList(user));
        return "/note/list";
    }

}
