package com.project.LIA.repository;

import com.project.LIA.domain.QnADomain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface QnARepository extends JpaRepository <QnADomain, Long> {

    // 페이지가 표시된 결과를 가져오는 방법
    Page<QnADomain> findAll(Pageable pageable);

}
