package com.project.LIA.service;


import com.project.LIA.domain.QnADomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;


public interface QnAService {

    List<QnADomain> getAllQnA();

//    QnADomain createQnA(QnADomain qnalist);

    QnADomain getQnAById(Long user);

    QnADomain updateQnA(Long userId, QnADomain question);

    void deleteQnA(Long userId);

    List<QnADomain> getAllQnA(Pageable pageable);   // 페이징해더

}
