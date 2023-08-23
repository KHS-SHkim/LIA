package com.project.LIA.repository;

import com.project.LIA.domain.BookDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<BookDomain,Long> {

    // 카테고리별 글 리스트
    @Query("SELECT b FROM BookDomain b WHERE b.cate = :cate")
    List<BookDomain> findByCate(@Param("cate") String cate);

    // 검색 리스트
    @Query("SELECT b FROM BookDomain b " +
            "WHERE b.title like '%' || #(keyword) || '%'")
    List<BookDomain> findByKeyWord(@Param("keyword") String keyword);
}
