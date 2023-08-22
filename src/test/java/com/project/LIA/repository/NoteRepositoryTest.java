package com.project.LIA.repository;

import com.project.LIA.domain.*;
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





//    @Test
    void testDataInsert (){

    }

    @Test
    void noteTest(){
        insUser();
        for (int i = 0 ; i < 10 ; i++ ){
            UserDomain user1 = randomUser();
            UserDomain user2 = randomUser();
            while(user1.getId() == user2.getId()){
                user2 = randomUser();
            }
            // Note test용;
            NoteDomain note = new NoteDomain();
            note.setUser(user1);
            note.setContents("아무말이나 일단 막 짓걸이자.");
            note.setReceiver(user2);
            note.setBook(insBook());
            note.setReceptionChk(1L);

            noteRepository.save(note);
        }
        noteRepository.findAll().forEach(System.out::println);

    }


    BookDomain insBook(){
        BookDomain book = BookDomain.builder()
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
                .build();

        UserDomain user2 = UserDomain.builder()
                .email("user2@naver.com")
                .phone("01011112222")
                .nickname("박승기 팀장")
                .password(passwordEncoder.encode("1234"))
                .username("user2")
                .build();
        UserDomain user3 = UserDomain.builder()
                .email("user3@naver.com")
                .phone("01033334444")
                .nickname("고태현 팀원")
                .password(passwordEncoder.encode("1234"))
                .username("user3")
                .build();
        UserDomain user4 = UserDomain.builder()
                .email("user4@naver.com")
                .phone("01044445555")
                .nickname("이가연 팀원")
                .password(passwordEncoder.encode("1234"))
                .username("user4")
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