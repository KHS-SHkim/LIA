package com.project.LIA.service;

import com.project.LIA.domain.DeclarationDomain;
import com.project.LIA.domain.UserDomain;
import com.project.LIA.repository.DeclarationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeclarationServiceImpl implements DeclarationService {

    @Autowired
    DeclarationRepository declarationRepository;
    // 신고조회 ( 전체 )
    @Override
    public List<DeclarationDomain> findDeclarations() {
        return declarationRepository.findAll();
    }

    // 신고조회 ( A to B )
    @Override
    public List<DeclarationDomain> findDeclarationAtoB(UserDomain user, UserDomain reporter) {
        return declarationRepository.findByUserIdAndReporterId(user.getId(), reporter.getId());
    }

    // 내가 신고한 리스트
    @Override
    public List<DeclarationDomain> findMyDeclaration(UserDomain user) {
        return declarationRepository.findByUserId(user.getId());
    }

    // 내가 신고당한 리스트
    @Override
    public List<DeclarationDomain> findDeclarationReporter(UserDomain reporter) {
        return declarationRepository.findByReporterId(reporter.getId());
    }

    // 신고 상세 내역 조회
    @Override
    public DeclarationDomain findDeclarationDetail(DeclarationDomain declarationDomain) {
        return declarationRepository.findById(declarationDomain.getId()).orElse(null);
    }
}
