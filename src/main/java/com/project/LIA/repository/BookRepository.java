package com.project.LIA.repository;

import com.project.LIA.domain.BookDomain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookDomain,Long> {
}
