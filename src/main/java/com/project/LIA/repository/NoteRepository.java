package com.project.LIA.repository;

import com.project.LIA.domain.NoteDomain;
import com.project.LIA.domain.UserDomain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<NoteDomain, Long> {

    // 쪽지 찾기 ( 사용자 , 수신인 )
    List<NoteDomain> findByUserIdAndReceiverId(Long user_id, Long receiver_id);

    // 쪽지 찾기 ( 내가 쓴 쪽지 )
    List<NoteDomain> findByUserId(Long user_id);

    // 쪽지 찾기 ( 내가 받은 쪽지 )
    List<NoteDomain> findByReceiverId(Long receiver_id);

    List<NoteDomain> findByUserIdOrReceiverId(Long user_id, Long receiver_id);

}
