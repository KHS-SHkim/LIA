package com.project.LIA.service;

import com.project.LIA.domain.BookImgDomain;
import org.springframework.stereotype.Service;


public interface BookImgService {
    BookImgDomain findById(long id);
}
