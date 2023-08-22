package com.project.LIA.service;

import com.project.LIA.domain.QnADomain;
import com.project.LIA.repository.QnARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QnAServiceImpl implements QnAService {

   private QnARepository qnaRepository;
   @Autowired
   public QnAServiceImpl(QnARepository qnARepository){
       this.qnaRepository = qnaRepository;
   }
   @Override    // 질문 리스트
   public List<QnADomain> getAllQnA(){
       return qnaRepository.findAll();
   }
   @Override    // 해당 Id 질문 하기
   public  QnADomain createQnA(QnADomain qna){
       return qnaRepository.save(qna);
   }
   @Override    // 해당 Id 질문 검색
   public QnADomain getQnAById(Long userId) {
        return qnaRepository.findById(userId).orElse(null);
   }

   @Override    // 해당 Id QnA 항목 수정
   public QnADomain updateQnA(Long userId, QnADomain updatedQnA) {
        QnADomain existingQnA = qnaRepository.findById(userId).orElse(null);
       if (existingQnA != null) {
           existingQnA.setQuestion(updatedQnA.getQuestion());
           existingQnA.setAnswer(updatedQnA.getAnswer());
           return qnaRepository.save(existingQnA);
       }
       return null;
   }

   @Override    // 해당 Id QnA 항목 삭제
   public void deleteQnA(Long userId) {
       qnaRepository.deleteById(userId);
   }


}
