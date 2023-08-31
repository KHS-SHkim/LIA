package com.project.LIA.controller;

import com.project.LIA.domain.BookDomain;
import com.project.LIA.domain.UserDomain;
import com.project.LIA.service.NoteService;
import com.project.LIA.service.UserService;
import com.project.LIA.util.U;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/note")
public class NoteController {

    @Autowired
    NoteService noteService;

    @Autowired
    UserService userService;

    @GetMapping("/list")
    public String noteList(Model model){
        UserDomain user = U.getLoggedUser();
//        UserDomain user = userService.findByUsername("user2");
        System.out.println("user = " + user);
        model.addAttribute("myNoteList", noteService.findMyNoteList(user));
        return "/note/list";
    }

    @GetMapping("/detail/{user_id}/{receiver_id}")
    public String noteDetail(Model model, @PathVariable Long user_id, @PathVariable Long receiver_id){

        UserDomain user = userService.findById(user_id);
        UserDomain receiver;
        if (user == U.getLoggedUser()){
//        if(user == userService.findByUsername("user2")) {
            receiver = userService.findById(receiver_id);
        } else {
            user = userService.findById(receiver_id);
            receiver = userService.findById(user_id);
        }

        if ( user != null && receiver != null ){
            model.addAttribute("noteList",noteService.findNote(user, receiver));
            model.addAttribute("user", user);
            model.addAttribute("receiver", receiver);
            model.addAttribute("lastNote", noteService.findLastNote(user, receiver));
        } else {
            model.addAttribute("noteList", null);

        }
        return "/note/detail";
    }

    @GetMapping("/write")
    public String noteWrite(@RequestParam("user") UserDomain user,
                            @RequestParam("receiver") UserDomain receiver,
                            @RequestParam("book") BookDomain book,
                            String contents){
        return "";
    }

}
