package com.project.LIA.domain;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Entity(name="note")
public class NoteDomain extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    // Note : User = N:1
    @ManyToOne
    UserDomain user;

    @ManyToOne
    UserDomain receiver;

    @ManyToOne
    BookDomain book;

    @Column(name = "contents", nullable = false, columnDefinition = "LONGTEXT")
    String contents;

    @Column(name = "reception_chk")
    Long receptionChk;


}
