package com.project.LIA.service;

import com.project.LIA.domain.DeclarationDomain;
import com.project.LIA.domain.UserDomain;

import java.util.List;

public interface DeclarationService {
    
    // 신고조회 ( 전체 )
    List<DeclarationDomain> findDeclarations();

    // 신고조회 ( A to B )
    List<DeclarationDomain> findDeclarationAtoB(UserDomain user, UserDomain reporter);

    // 내가 신고한 리스트
    List<DeclarationDomain> findMyDeclaration(UserDomain user);

    // 내가 신고당한 리스트
    List<DeclarationDomain> findDeclarationReporter(UserDomain reporter);
    
    // 신고 상세 내역 조회
    DeclarationDomain findDeclarationDetail(Long declaration_id);

    void write(DeclarationDomain declarationDomain);
}
