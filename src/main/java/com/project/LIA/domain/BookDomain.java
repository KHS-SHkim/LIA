package com.project.LIA.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "book")
public class BookDomain extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String cate; // 카테고리

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String rental_spot; // 대여지역

    @Column(nullable = false)
    private String book_detail; // content

    @Column(nullable = false)
    private String rental_stat; // 대여상태

    @Column(nullable = false)
    private int rental_date; // 대여기간

    @ManyToOne
    @ToString.Exclude
    private UserDomain user;

    // 첨부파일
    // Book:BookImg = 1:N
    //  cascade = CascadeType.ALL  : 삭제등의 동작 발생시 child 도 함께 삭제
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id")
    @ToString.Exclude
    @Builder.Default
    private List<BookImgDomain> fileList = new ArrayList<>();

    public void addFiles(BookImgDomain... files){
        Collections.addAll(fileList, files);
    }




}
