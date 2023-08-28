package com.project.LIA.repository;

import com.project.LIA.domain.BookImgDomain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookImgRepository extends JpaRepository<BookImgDomain, Long> {
    List<BookImgDomain> findByBook(long bookId);
}
