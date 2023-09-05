package com.project.LIA.service;


import com.project.LIA.domain.QnADomain;
import org.springframework.ui.Model;

import java.util.List;


public interface QnAService {

    // 질문글 전부 select
    List<QnADomain> getAllQnA(Integer page, Model model);

    // 질문글 검색 select
    List<QnADomain> listBySearch(String searchValue, Integer page, Model model);

    // 질문글 등록
    int write(QnADomain qnADomain);

    // 질문글 수정
    int update(QnADomain qnADomain);

    // 질문글 삭제
    int delete(QnADomain qnADomain);

    // 질문글 디테일
    QnADomain findById(long id);


}