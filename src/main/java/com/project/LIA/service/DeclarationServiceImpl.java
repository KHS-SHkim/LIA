package com.project.LIA.service;

import com.project.LIA.domain.DeclarationDomain;
import com.project.LIA.domain.UserDomain;
import com.project.LIA.repository.DeclarationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public DeclarationDomain findDeclarationDetail(Long declaration_id) {
        return declarationRepository.findById(declaration_id).orElse(null);
    }

    @Override
    public void write(DeclarationDomain declarationDomain) {
        declarationRepository.save(declarationDomain);
    }

    @Override
    public List<DeclarationDomain> findAnswerNoAnswer(String state) {
        List<DeclarationDomain> list = declarationRepository.findAll();
        List<DeclarationDomain> tmp = new ArrayList<>();
        if (state.equals("미답변리스트")){
            for(DeclarationDomain declaration : list){
                if (declaration.getAnswerContent()== "" || declaration.getAnswerContent() == null){
                    tmp.add(declaration);
                }
            }
            list = tmp;
        }

        return list;
    }
}
