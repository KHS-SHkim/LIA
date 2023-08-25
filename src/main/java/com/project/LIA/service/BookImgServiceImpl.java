package com.project.LIA.service;

import com.project.LIA.domain.BookImgDomain;
import com.project.LIA.repository.BookImgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookImgServiceImpl implements BookImgService{
    private BookImgRepository bookImgRepository;

    @Autowired
    public void setAttachmentRepository(BookImgRepository bookImgRepository) {

        this.bookImgRepository = bookImgRepository;
    }

    public BookImgServiceImpl() {
        System.out.println(getClass().getName() +"() 생성");
    }

    @Override
    public BookImgDomain findById(Long id) {
        return bookImgRepository.findById(id).orElse(null);
    }
}
