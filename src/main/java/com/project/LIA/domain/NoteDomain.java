package com.project.LIA.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity(name="note")
public class NoteDomain extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Note : User = N:1
    @ManyToOne(fetch = FetchType.LAZY, targetEntity= UserDomain.class )
    @ToString.Exclude
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private UserDomain user;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity= UserDomain.class )
    @ToString.Exclude
    @JoinColumn(name="receiver_id", referencedColumnName = "id")
    private UserDomain receiver;

//    @ManyToOne
//    @JoinColumn(name = "book_id", table = "book")
    @ManyToOne(fetch = FetchType.LAZY, targetEntity= BookDomain.class )
    @ToString.Exclude
    @JoinColumn(name="book_id", referencedColumnName = "id")
    private BookDomain book;

    @Column(name = "contents", nullable = false, columnDefinition = "LONGTEXT")
    private String contents;

    @Column(name = "reception_chk")
    private Long receptionChk;


    public NoteDomain (Long id, UserDomain user, UserDomain receiver, BookDomain book, String contents, Long receptionChk, LocalDateTime regDate) {
        this.id = id;
        this.user = user;
        this.receiver = receiver;
        this.book = book;
        this.contents = contents;
        this.receptionChk = receptionChk;
        super.setRegDate(regDate);
    }
}
