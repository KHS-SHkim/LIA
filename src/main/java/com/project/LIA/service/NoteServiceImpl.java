package com.project.LIA.service;

import com.project.LIA.domain.NoteDomain;
import com.project.LIA.domain.NoteSum;
import com.project.LIA.domain.UserDomain;
import com.project.LIA.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

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

    @Override   // 내가 쓰거나 받은쪽지 (쪽지함)
    public List<NoteDomain> findMyNoteList(UserDomain user) {
        List<NoteDomain> noteList = noteRepository.findByUserIdOrReceiverId(user.getId(), user.getId());
        List<NoteDomain> sendList = noteRepository.findByUserId(user.getId());
        List<NoteDomain> reciveList = noteRepository.findByReceiverId(user.getId());

        List<NoteDomain> sumNoteList = new ArrayList<>();
        List<NoteDomain> tmpList = new ArrayList<>();
        for(NoteDomain t : noteList){
            NoteDomain tmp = null;
            // 내가 보낸 쪽지
            if ( t.getUser().getId().equals(user.getId())){
                for(NoteDomain y : reciveList){

                }
                if ( tmp == null ){
                    tmp = t;
                } else if (tmp.getId() < t.getId()) {
                    tmp = t;
                }
                tmpList.add(tmp);
            }
            // 내가 받은 쪽지
            else if (t.getReceiver().getId().equals(user.getId())){
                if ( tmp == null ){
                    tmp = t;
                } else if (tmp.getId() < t.getId()) {
                    tmp = t;
                }
                tmpList.add(tmp);
            }
        }
        System.out.println("chk   ::::::::::::");
        tmpList.forEach(System.out::println);
        return sumNoteList;
    }
}