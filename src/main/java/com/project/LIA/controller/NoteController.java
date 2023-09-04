package com.project.LIA.controller;

import com.project.LIA.domain.BookDomain;
import com.project.LIA.domain.NoteAjaxData;
import com.project.LIA.domain.NoteDomain;
import com.project.LIA.domain.UserDomain;
import com.project.LIA.service.BookService;
import com.project.LIA.service.NoteService;
import com.project.LIA.service.UserService;
import com.project.LIA.util.U;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/note")
public class NoteController {

    @Autowired
    NoteService noteService;

    @Autowired
    UserService userService;

    @Autowired
    BookService bookService;



    @GetMapping("/list")
    public String noteList(Model model){
        UserDomain user = U.getLoggedUser();
//        UserDomain user = userService.findByUsername("user2");
        model.addAttribute("myNoteList", noteService.findMyNoteList(user));
        model.addAttribute("loginUser", user);
        return "/note/list";
    }

    @GetMapping("/detail/{user_id}/{receiver_id}")
    public String noteDetail(Model model, @PathVariable Long user_id, @PathVariable Long receiver_id){
        UserDomain user = userService.findById(user_id);
        UserDomain receiver;
        UserDomain logInUser = U.getLoggedUser();
        if (user.getId().equals(logInUser.getId())){
            receiver = userService.findById(receiver_id);
        } else {
            user = userService.findById(receiver_id);
            receiver = userService.findById(user_id);
        }
        List<NoteDomain> noteList  = noteService.findNote(user, receiver);

        for (NoteDomain note : noteList) {
            if (receiver.getId().equals(logInUser.getId()) && note.getReceptionChk() == 1L){
                note.setReceptionChk(0L);
                noteService.writeNote(note);
            }
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
    public String getNoteWrite( @RequestParam(value = "book_id") String book_id,
                            Model model){
        BookDomain book = bookService.selectById(Long.parseLong(book_id));
        UserDomain user = U.getLoggedUser();
        UserDomain receiver = book.getUser();
        List<NoteDomain> note = noteService.findNote(user, receiver);
        System.out.println(note);

        model.addAttribute("user", user);
        model.addAttribute("receiver", receiver);
        model.addAttribute("book", book);
        model.addAttribute("noteList", note);


        return "/note/write";
    }


    @ResponseBody
    @PostMapping("/write")
    public void noteWrite(@RequestParam(value = "user_id") String user_id,
                            @RequestParam(value = "receiver_id") String receiver_id,
                            @RequestParam(value = "book_id") String book_id,
                            @RequestParam("contents")String contents,
                            Model model){
        UserDomain user = userService.findById(Long.parseLong(user_id));
        UserDomain receiver = userService.findById(Long.parseLong(receiver_id));
        NoteDomain note = NoteDomain.builder()
                .user(user)
                .receiver(receiver)
                .book(bookService.selectById(Integer.parseInt(book_id)))
                .contents(contents)
                .receptionChk(1L)
                .build();

        noteService.writeNote(note);

        model.addAttribute("user", user);
        model.addAttribute("receiver", receiver);
    }

    @GetMapping("/noteStart/{user_id}/{receiver_id}/{book_id}")
    public String noteStart(Model model, @PathVariable Long user_id, @PathVariable Long receiver_id, @PathVariable Long book_id){
        model.addAttribute("userInfo",userService.findById(user_id));
        model.addAttribute("receiverInfo", userService.findById(receiver_id));
        model.addAttribute("bookInfo", bookService.selectById(book_id));
        return "/note/noteStart";
    }

}
