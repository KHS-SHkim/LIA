package com.project.LIA.service;

import com.project.LIA.domain.NoteAjaxData;
import com.project.LIA.domain.NoteDomain;
import com.project.LIA.domain.UserDomain;
import com.project.LIA.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    NoteRepository noteRepository;


    @Override   // 쪽지 찾기 ( 사용자 , 수신인 )
    public List<NoteDomain> findNote(UserDomain user, UserDomain receiver) {
        List<NoteDomain> retunList = new ArrayList<>();
        List<NoteDomain> t = noteRepository.findByUserIdAndReceiverId(user.getId(), receiver.getId());
        List<NoteDomain> y = noteRepository.findByUserIdAndReceiverId(receiver.getId(),user.getId());

        retunList.addAll(t);
        retunList.addAll(y);

        retunList.sort(Comparator.comparing(NoteDomain::getId));
        return retunList;
    }

    @Override   // 쪽지 찾기 ( 내가 쓴 쪽지 )
    public List<NoteDomain> findMyNote(UserDomain user) {
        List<NoteDomain> retunList= noteRepository.findByUserId(user.getId());
        retunList.sort(Comparator.comparing(NoteDomain::getId).reversed());
        return retunList;
    }

    @Override   // 쪽지 찾기 ( 내가 받은 쪽지 )
    public List<NoteDomain> findreceivNote(UserDomain receiver) {
        List<NoteDomain> retunList= noteRepository.findByReceiverId(receiver.getId());
        retunList.sort(Comparator.comparing(NoteDomain::getId).reversed());
        return retunList;
    }

    @Override   // 쪽지 쓰기 ( user = 작성자 / receiver = 상대 )
    public void writeNote(NoteDomain note) {
        noteRepository.save(note);
    }

    @Override   // 내가 쓰거나 받은쪽지 (쪽지함)
    public List<NoteDomain> findMyNoteList(UserDomain user) {
        List<NoteDomain> noteList = noteRepository.findByUserIdOrReceiverId(user.getId(), user.getId());
        List<NoteDomain> sumNoteList = new ArrayList<>();
        List<Long[]> tmpList = new ArrayList<>();
        List<Long> tmpList2 = new ArrayList<>();
        for (NoteDomain t : noteList){
            if (t.getUser().getId() < t.getReceiver().getId()){
                Long [] arr = {t.getId(), t.getUser().getId(), t.getReceiver().getId()};
                tmpList.add(arr);
            }
            if (t.getUser().getId() > t.getReceiver().getId()){
                Long [] arr = {t.getId(), t.getReceiver().getId(), t.getUser().getId()};
                tmpList.add(arr);
            }
        }

        for (Long [] t : tmpList){
            Long [] o = t;
            for (Long[] x : tmpList){
                if (t[1] == x[1] && t[2] == x[2] && t[0] <= x[0]){
                    o = x;
                }
            }
            if (tmpList2.isEmpty() || tmpList2.get(tmpList2.size()-1) != o[0] ){
                tmpList2.add(o[0]);
            }
        }

        System.out.println("chk   ::::::::::::");
        sumNoteList = noteRepository.findAllById(tmpList2);
        sumNoteList.sort(Comparator.comparing(NoteDomain::getId).reversed());
        return sumNoteList;
    }

    @Override
    public NoteDomain findLastNote(UserDomain user, UserDomain receiver) {
        List<NoteDomain> i = noteRepository.findByUserIdAndReceiverId(user.getId(), receiver.getId());
        List<NoteDomain> y = noteRepository.findByUserIdAndReceiverId(receiver.getId(),user.getId());
        i.addAll(y);
        i.sort(Comparator.comparing(NoteDomain::getId).reversed());
        return i.get(0);
    }

    @Override
    public NoteAjaxData findNoteAjax(UserDomain user, UserDomain receiver) {
        NoteAjaxData returnList = new NoteAjaxData();

        List<NoteDomain> list= findNote(user, receiver);
        for(NoteDomain note : list){
            note.setUserInfo(note.getUser().getId());
            note.setUserNickname(note.getUser().getNickname());
            note.setReceiverInfo(note.getReceiver().getId());
            note.setReceiverNickname(note.getReceiver().getNickname());
            note.setBookInfo(note.getBook().getId());
        }

        returnList.setList(list);
        returnList.setUser(user);
        returnList.setReceiver(receiver);

        System.out.println(returnList);

        return returnList;



    }
}