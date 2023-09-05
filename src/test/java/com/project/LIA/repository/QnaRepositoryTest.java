package com.project.LIA.repository;

import com.project.LIA.domain.AuthorityDomain;
import com.project.LIA.domain.QnADomain;
import com.project.LIA.domain.UserDomain;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootTest
class QnaRepositoryTest {
    @Autowired
    private QnaRepository qnARepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Test
    void QnATest(){
        System.out.println("#QnATEST --------------------------------------------------------- \n");


        // Authority 생성
        AuthorityDomain auth_member = AuthorityDomain.builder()
                        .name("ROLE_ADMIN")
                        .build();
        AuthorityDomain auth_admin = AuthorityDomain.builder()
                        .name("ROLE_MEMBER")
                        .build();

        authorityRepository.saveAndFlush(auth_admin); // INSERT
        authorityRepository.saveAndFlush(auth_member);  // INSERT

        authorityRepository.findAll().forEach(System.out::println); // SELECT

        // User 생성
        UserDomain user1 = UserDomain.builder()
                .username("USER1")
                .password(passwordEncoder.encode("1234"))
                .email("abc123@unknow.com")
                .phone("010-3212-1111")
                .nickname("회원1")
                .build();

        UserDomain user2 = UserDomain.builder()
                .username("USER2")
                .password(passwordEncoder.encode("1234"))
                .email("dcx@know.com")
                .phone("010-0000-0111")
                .nickname("회원2")
                .build();

        UserDomain admin1 = UserDomain.builder()
                .username("ADMIN1")
                .password(passwordEncoder.encode("1234"))
                .email("ad23@unknow.com")
                .phone("010-0001-0002")
                .nickname("관리자1")
                .build();

        user1.addAuthority(auth_member);
        admin1.addAuthority(auth_member, auth_admin);

        userRepository.saveAll(List.of(user1, user2, admin1));

        userRepository.findAll().forEach(System.out::println);

        // QnA 생성
        QnADomain Q1 = QnADomain.builder()
                        .question( "질문이에요.01" )
                        .answer("답변글")
                        .user_id(user1)
                        .build();
        QnADomain Q2 = QnADomain.builder()
                        .question("질문 글")
                        .answer( "답변글입니다.01" )
                        .user_id(admin1)
                        .build();

        QnADomain Q3 = QnADomain.builder()
                        .question( "질문이에요.02" )
                        .answer("답변글")
                        .user_id(user2)
                        .build();
        QnADomain Q4 = QnADomain.builder()
                        .question("질문 글")
                        .answer( "답변글입니다.02" )
                        .user_id(admin1)
                        .build();

        qnARepository.saveAll(List.of(Q1,Q2,Q3,Q4));
        System.out.println("\n[QnA]");
        qnARepository.findAll().forEach(System.out::println);


        System.out.println("\n ------------------------------------------------------------ \n");
    }



}