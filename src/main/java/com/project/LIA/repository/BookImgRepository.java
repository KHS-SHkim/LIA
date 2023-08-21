package com.project.LIA.repository;

import com.project.LIA.domain.BookImgDomain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookImgRepository extends JpaRepository<BookImgDomain, Long> {
}
