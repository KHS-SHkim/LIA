package com.project.LIA.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "book_img")
public class BookImgDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; //PK

    @Column(nullable = false)
    private String img_src;   //파일명

    @Column(name = "book_id")
    private int book;   // 어느글의 첨부파일? (FK)

}
