package com.project.LIA.service;

import com.project.LIA.domain.QnADomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
public class QnAServiceImpl implements QnAService {
//
//    @Autowired
//    private  QnARepository qnaRepository;
//
//    @Override    // 질문 리스트
//    public List<QnADomain> getAllQnA(){
//        return qnaRepository.findAll();
//    }
//    @Override    // 해당 Id 질문 하기
//    public  QnADomain createQnA(QnADomain qna){
//        return qnaRepository.save(qna);
//    }
//    @Override    // 해당 Id 질문 검색
//    public QnADomain getQnAById(Long userId) {
//        return qnaRepository.findById(userId).orElse(null);
//    }
//
//    @Override    // 해당 Id QnA 항목 수정
//    public QnADomain updateQnA(Long userId, QnADomain updatedQnA) {
//        QnADomain existingQnA = qnaRepository.findById(userId).orElse(null);
//        if (existingQnA != null) {
//            existingQnA.setQuestion(updatedQnA.getQuestion());
//            existingQnA.setAnswer(updatedQnA.getAnswer());
//            return qnaRepository.save(existingQnA);
//        }
//        return null;
//    }
//
//    @Override    // 해당 Id QnA 항목 삭제
//    public void deleteQnA(Long userId) {
//        qnaRepository.deleteById(userId);
//    }
//
//    @Override   // 페이지가 표시된 질문 및 답변 목록 검색
//    public Page<QnADomain> getAllQnA(Pageable pageable) {
//
//        return qnaRepository.findAll(pageable);
//    }


}
