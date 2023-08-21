package com.project.LIA.repository;

import com.project.LIA.domain.NoteDomain;
import com.project.LIA.domain.UserDomain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<NoteDomain, Long> {

    // 쪽지 찾기 ( 사용자 , 수신인 )
    List<NoteDomain> findByUserIdAndReceiverId(UserDomain user, UserDomain receiver);

    // 쪽지 찾기 ( 내가 쓴 쪽지 ) findById 는 만들필요없음


    // 쪽지 찾기 ( 내가 받은 쪽지 )
    List<NoteDomain> findByReceiverId(UserDomain receiver);

//    List<User> findByEmailAndName(String email, String name);
}
