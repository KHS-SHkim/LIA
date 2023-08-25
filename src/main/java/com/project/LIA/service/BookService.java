package com.project.LIA.service;

import com.project.LIA.domain.BookDomain;
import com.project.LIA.repository.BookImgRepository;
import com.project.LIA.repository.BookRepository;
import com.project.LIA.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface BookService {

    // 1. 글 작성
    int write(BookDomain book);    // -첨부파일 X
    int write(BookDomain book, Map<String, MultipartFile> files);    // -첨부파일 O

    //2. 단일 글 보기
    BookDomain selectById(int id);

    //3. 글 리스트
    List<BookDomain> list();    // -모든 글 리스트
    List<BookDomain> cateList(String cate,Integer page, Model model);    // -카테고리별 리스트
    List<BookDomain> searchList(String keyword,Integer page, Model model);    // -검색 리스트
    // 페이징 리스트
    List<BookDomain> list(Integer page, Model model);

    //4. 글 수정
    int update(BookDomain book);    // -첨부파일 X
    int update(BookDomain book      // -첨부파일 O
            , Map<String, MultipartFile> files
            , Long[] delfile);

    //5. 글 삭제
    int deleteById(int id);



}
