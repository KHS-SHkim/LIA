package com.project.LIA.repository;

import com.project.LIA.domain.QnADomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface QnaRepository extends JpaRepository<QnADomain, Long> {

    // 검색 리스트 조회
    Page<QnADomain> findByQuestionContaining(String searchValue, Pageable pageable);

}
