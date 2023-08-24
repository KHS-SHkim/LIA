package com.project.LIA.service;

import com.project.LIA.domain.NoteDomain;
import com.project.LIA.domain.NoteSum;
import com.project.LIA.domain.UserDomain;
import com.project.LIA.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    NoteRepository noteRepository;


    @Override   // 쪽지 찾기 ( 사용자 , 수신인 )
    public List<NoteDomain> findNote(UserDomain user, UserDomain receiver) {
        return noteRepository.findByUserIdAndReceiverId(user.getId(), receiver.getId());
    }

    @Override   // 쪽지 찾기 ( 내가 쓴 쪽지 )
    public List<NoteDomain> findMyNote(UserDomain user) {
        return noteRepository.findByUserId(user.getId());
    }

    @Override   // 쪽지 찾기 ( 내가 받은 쪽지 )
    public List<NoteDomain> findreceivNote(UserDomain receiver) {
        return noteRepository.findByReceiverId(receiver.getId());
    }

    @Override   // 쪽지 쓰기 ( user = 작성자 / receiver = 상대 )
    public void writeNote(NoteDomain note) {
        noteRepository.save(note);
    }

    @Override   // 내가 쓰거나 받은쪽지
    public List<NoteDomain> findMyNoteList(UserDomain user) {
        List<NoteDomain> noteList;
        noteList = noteRepository.findAll();
        List<NoteDomain> retNoteList = new ArrayList<NoteDomain>();
//        for (NoteDomain note : noteList) {
//            UserDomain sendUser = note.getUser();
//            UserDomain receiveUser = note.getReceiver();
//            if (sendUser.getId().equals(user.getId())) {
//                int t = retNoteList.size();
//                if (t == 0) {
//                    retNoteList.set(1,note);
//                }else{
//                    retNoteList.set(retNoteList.size()+1,note);
//                }
//
//            }
////            receiveUser.getId().equals(user.getId())
//        }
//        retNoteList.forEach(System.out::println);

        return null;
    }
}