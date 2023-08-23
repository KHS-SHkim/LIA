package com.project.LIA.controller;

import com.project.LIA.domain.QnADomain;
import com.project.LIA.service.QnAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/qna")
public class QnAController {
    @Autowired
    private QnAService qnaService;

    public QnAController(QnAService qnaService) {
        this.qnaService = qnaService;
    }

    @GetMapping("/anq") // 질문 리스트
    public List<QnADomain> getAllQnA(){
        return qnaService.getAllQnA();
    }
    @GetMapping("/qna/{userId}")    // 해당 id로 특정 QnA 항목 검색
    public QnADomain getQnAById(@PathVariable long userId){
        return qnaService.getQnAById(userId);

    }
    @PostMapping("/qna")    // 해당 id로 특정 QnA 항목 질문하기
    public QnADomain createQnA(@RequestBody QnADomain qna){
        return qnaService.createQnA(qna);
    }
    @PostMapping("/qna/{userId}")   // 해당 id로 특정 QnA 항목 업데이트
    public QnADomain updateQnA(@PathVariable long userId, @RequestBody QnADomain updatedQnA ){
        return qnaService.updateQnA(userId, updatedQnA);
    }

    @DeleteMapping("/qna/{userId}") // 해당 id로 QnA 항목을 삭제합니다.
    public void deleteQnA(@PathVariable long userId) {
        qnaService.deleteQnA(userId);
    }







}
