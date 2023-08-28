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
    private long id; //PK

    @Column(nullable = false)
    private String img_src;   //파일명

    @Column(name = "book_id")
    private long book;   // 어느글의 첨부파일? (FK)

    @Transient
    private boolean isImage;   // 이미지 여부

}
