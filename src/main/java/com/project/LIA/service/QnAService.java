package com.project.LIA.service;


import com.project.LIA.domain.QnADomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;


public interface QnAService {

    List<QnADomain> getAllQnA();

//    QnADomain createQnA(QnADomain qnalist);

    QnADomain getQnAById(Long user_id);

    QnADomain updateQnA(Long user_id, QnADomain question);

    void deleteQnA(Long user_id);

    List<QnADomain> getAllQnA(Pageable pageable);   // 페이징해더

//    List<QnADomain> searchQnA(String query); // 검색기능
}
