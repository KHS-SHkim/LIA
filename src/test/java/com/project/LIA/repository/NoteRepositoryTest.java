package com.project.LIA.repository;

import com.project.LIA.domain.*;
import com.project.LIA.service.NoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;

@SpringBootTest
class NoteRepositoryTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private BookImgRepository bookImgRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QnARepository qnARepository;

    @Autowired
    private NoteService noteService;





//    @Test
    void testDataInsert (){

    }

    @Test
    void noteTest(){
//        insUser();
        UserDomain user1 = new UserDomain();
        UserDomain user2 = new UserDomain();
        BookDomain book = new BookDomain();
        for (int i = 0 ; i < 10 ; i++ ){
            user1 = randomUser();
            user2 = randomUser();
            while(user1.getId() == user2.getId()){
                user2 = randomUser();
            }
            book = insBook();
            // Note test용;
            NoteDomain note = new NoteDomain();
            note.setUser(user1);
            note.setContents("아무말이나 일단 막 짓걸이자. ["+i+"]");
            note.setReceiver(user2);
            note.setBook(book);
//            note.setBook(book);
            note.setReceptionChk(1L);

            noteRepository.save(note);
        }
        noteRepository.findAll().forEach(System.out::println);

        System.out.println("---- 쪽지 찾기 ( 사용자 , 수신인 ) ----");
        noteRepository.findByUserIdAndReceiverId(user1.getId() ,user2.getId()).forEach(System.out::println);
        System.out.println("---- 쪽지 찾기 ( 내가 쓴 쪽지 ) ----");
        noteRepository.findByUserId(user1.getId()).forEach(System.out::println);
//        noteRepository.findAllBy().forEach(System.out::println);
        System.out.println("---- 쪽지 찾기 ( 내가 받은 쪽지 ) ----");
        noteRepository.findByReceiverId(user1.getId()).forEach(System.out::println);



        // QnA 생성
        QnADomain Q1 = QnADomain.builder()
                .question( "질문이에요.01" )
                .user_id(user1)
                .build();
        QnADomain Q2 = QnADomain.builder()
                .question("질문 글")
                .answer( "답변글입니다.01" )
                .user_id(user1)
                .build();

        QnADomain Q3 = QnADomain.builder()
                .question( "질문이에요.02" )
                .user_id(user2)
                .build();
        QnADomain Q4 = QnADomain.builder()
                .question("질문 글")
                .answer( "답변글입니다.02" )
                .user_id(user1)
                .build();

        qnARepository.saveAll(List.of(Q1,Q2,Q3,Q4));
        System.out.println("\n[QnA]");
        qnARepository.findAll().forEach(System.out::println);


        System.out.println("\n ------------------------------------------------------------ \n");


    }



    BookDomain insBook(){
        BookDomain book = BookDomain.builder()
                .book_detail("book_detail")
                .user(randomUser())
                .cate("01")
                .price(1000)
                .rental_stat("01")
//                .rental_date(7)
                .rental_spot("서울시 중랑구")
                .name("아무책이나 등록해보자")
                .build();
        bookRepository.save(book);
        return book;
    }

    void insUser(){
        UserDomain user1 = UserDomain.builder()
                .email("user1@naver.com")
                .phone("01022223333")
                .nickname("김홍석 조원")
                .password(passwordEncoder.encode("1234"))
                .username("user1")
                .state("USE")
                .build();

        UserDomain user2 = UserDomain.builder()
                .email("user2@naver.com")
                .phone("01011112222")
                .nickname("박승기 팀장")
                .password(passwordEncoder.encode("1234"))
                .username("user2")
                .state("USE")
                .build();
        UserDomain user3 = UserDomain.builder()
                .email("user3@naver.com")
                .phone("01033334444")
                .nickname("고태현 팀원")
                .password(passwordEncoder.encode("1234"))
                .username("user3")
                .state("USE")
                .build();
        UserDomain user4 = UserDomain.builder()
                .email("user4@naver.com")
                .phone("01044445555")
                .nickname("이가연 팀원")
                .password(passwordEncoder.encode("1234"))
                .username("user4")
                .state("USE")
                .build();

        userRepository.saveAll(List.of(user1, user2, user3, user4));
    }

    UserDomain randomUser(){
        List<UserDomain> users = userRepository.findAll();
        while(true){
            int i = (int)(Math.random()*10);

            if(i>= 0 && i <4){
                return users.get(i);
            }
        }
    }


    void insAuthority(){
        AuthorityDomain auth_member = AuthorityDomain.builder()
                .name("ROLE_MEMBER")
                .build();
        AuthorityDomain auth_admin = AuthorityDomain.builder()
                .name("ROLE_ADMIN")
                .build();
        authorityRepository.saveAndFlush(auth_member);
        authorityRepository.saveAndFlush(auth_admin);

    }
    void addAuthority(List<AuthorityDomain> authList, AuthorityDomain... authorities){
        if(authorities != null){
            Collections.addAll(authList, authorities);
        }
    }

}