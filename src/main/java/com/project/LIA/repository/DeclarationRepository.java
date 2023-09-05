package com.project.LIA.repository;

import com.project.LIA.domain.DeclarationDomain;
import com.project.LIA.domain.NoteDomain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeclarationRepository extends JpaRepository<DeclarationDomain, Long> {
    // 신고 조회 ( 내가 특정 상대를 )
    List<DeclarationDomain> findByUserIdAndReporterId(Long user_id, Long reporter_id);

    // 내가 신고한 리스트
    List<DeclarationDomain> findByUserId(Long user_id);

    // 내가 신고 당한 리스트
    List<DeclarationDomain> findByReporterId(Long reporter_id);



}
