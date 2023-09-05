package com.project.LIA.controller;

import com.project.LIA.domain.QnADomain;
import com.project.LIA.service.QnAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Controller
@RequestMapping("/qna")
public class QnAController {
    @Autowired
    private QnAService qnaService;

    public QnAController() {
        System.out.println(getClass().getName() + "()생성");
    }

    @GetMapping("/list") // 질문 리스트 // 페이지가 표시된 질문 및 답변 목록 검색
    public String getAllQnA(Model model, @PageableDefault(size = 10)Pageable pageable){
        List<QnADomain> qnADomainList = qnaService.getAllQnA(pageable);
        model.addAttribute("qnADomainList",qnADomainList);
        return "qna/list";
    }
    @GetMapping("/list/{user_id}")    // 해당 id로 특정 QnA 항목 검색
    public String getQnAById(@PathVariable long user_id, Model model){
        model.addAttribute("post", qnaService.getQnAById(user_id));
        return "list/user_id";
    }

////    @PostMapping("/update")    // 해당 id로 특정 QnA 항목 질문하기
////    public QnADomain createQnA(@RequestBody QnADomain question){
////        return qnaService.createQnA(qna);
////    }
    @PostMapping("/update/{user_id}")   // 해당 id로 특정 QnA 항목 질문
    public String updateQnA(@PathVariable long user_id, @RequestBody QnADomain question ){
        System.out.println(qnaService.updateQnA(user_id, question));

        return "qna/update";
    }

    @PostMapping("/delete/{id}") // 해당 id로 QnA 항목을 삭제합니다.
    public void deleteQnA(@PathVariable long user_id) {
        qnaService.deleteQnA(user_id);
    }


//    @GetMapping("/search")  // QnA 검색기능
//    public String searchQnA(@RequestParam String query, Model model) {
//        List<QnADomain> searchResults =  qnaService.searchQnA(query);
//        model.addAttribute("qnADomainList",searchResults);
//        return "list";
//    }


}
