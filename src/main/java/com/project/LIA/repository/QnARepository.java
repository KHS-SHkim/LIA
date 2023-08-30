package com.project.LIA.repository;

import com.project.LIA.domain.QnADomain;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;


public interface QnARepository extends JpaRepository<QnADomain, Long> {

    // Retrieve a specific QnA entry by user ID
//    QnADomain getQnAById(Long user);

    // Update a specific QnA entry by user ID
//    QnADomain updateQnA(Long user, QnADomain question);

    // 모든 QnA 항목 list
    List<QnADomain> findAll();

    QnADomain save(QnADomain question);

    Optional<QnADomain> findById(Long user_id);

    void deleteById(Long user_id);

    List<QnADomain> findByQuestionContaining(String query);
}

