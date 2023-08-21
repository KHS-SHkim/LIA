package com.project.LIA.service;

import com.project.LIA.domain.NoteDomain;
import com.project.LIA.domain.UserDomain;
import org.springframework.stereotype.Service;

@Service
public interface NoteService {
    // 쪽지 찾기 ( 사용자 , 수신인 )
    NoteDomain findNote(UserDomain user, UserDomain receiver);

    // 쪽지 찾기 ( 내가 쓴 쪽지 )
    NoteDomain findMyNote(UserDomain user);

    // 쪽지 찾기 ( 내가 받은 쪽지 )
    NoteDomain findreceivNote(UserDomain receiver);

}
