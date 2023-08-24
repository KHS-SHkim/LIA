package com.project.LIA.service;


import com.project.LIA.domain.QnADomain;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;


public interface QnAService{
    List<QnADomain>getAllQnA();
    QnADomain createQnA(QnADomain qnalist);

    QnADomain getQnAById(Long userId);

    QnADomain updateQnA(Long userId, QnADomain updatedQnA);

    void deleteQnA(Long userId);

    Page<QnADomain> getAllQnA(Pageable pageable);   // 페이징해더


}
