package com.project.LIA.repository;

import com.project.LIA.domain.BookDomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<BookDomain,Long> {

    // 카테고리별 글 리스트
    Page<BookDomain> findByCate(String cate, Pageable pageable);

    // 검색 리스트
    Page<BookDomain> findByNameContaining(String keyword,Pageable pageable);

    // 내가 쓴 글 리스트
    Page<BookDomain> findByUserUsername(String username, Pageable pageable);
}
