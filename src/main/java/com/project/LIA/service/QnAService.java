package com.project.LIA.service;


import com.project.LIA.domain.QnADomain;
import com.project.LIA.repository.QnARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


public interface QnAService {
    List<QnADomain>getAllQnA();
    QnADomain createQnA(QnADomain qnalist);

    QnADomain getQnAById(Long userId);

    QnADomain updateQnA(Long userId, QnADomain updatedQnA);

    void deleteQnA(Long userId);
}
