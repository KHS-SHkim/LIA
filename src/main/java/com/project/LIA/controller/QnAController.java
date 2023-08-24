package com.project.LIA.controller;

import com.project.LIA.domain.QnADomain;
import com.project.LIA.service.QnAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
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

    @GetMapping("/list") // 질문 리스트
    public List<QnADomain> getAllQnA(){
        return qnaService.getAllQnA();
    }
    @GetMapping("/list/{userId}")    // 해당 id로 특정 QnA 항목 검색
    public QnADomain getQnAById(@PathVariable long userId){
        return qnaService.getQnAById(userId);

    }
    @PostMapping("/update")    // 해당 id로 특정 QnA 항목 질문하기
    public QnADomain createQnA(@RequestBody QnADomain qna){
        return qnaService.createQnA(qna);
    }
    @PostMapping("/update/{userId}")   // 해당 id로 특정 QnA 항목 업데이트
    public QnADomain updateQnA(@PathVariable long userId, @RequestBody QnADomain updatedQnA ){
        return qnaService.updateQnA(userId, updatedQnA);
    }

    @DeleteMapping("/delete/{userId}") // 해당 id로 QnA 항목을 삭제합니다.
    public void deleteQnA(@PathVariable long userId) {
        qnaService.deleteQnA(userId);
    }

    @GetMapping("/anq") // 페이지가 표시된 질문 및 답변 목록 검색
    public Page<QnADomain> getAllQnA(@PageableDefault(size = 10)Pageable pageable) {
        return  qnaService.getAllQnA(pageable);
    }


}
