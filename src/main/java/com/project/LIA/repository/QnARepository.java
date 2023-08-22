package com.project.LIA.repository;

import com.project.LIA.domain.QnADomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QnARepository extends JpaRepository <QnADomain, Long> {



}
