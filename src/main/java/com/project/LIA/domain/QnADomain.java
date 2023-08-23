package com.project.LIA.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "QnA")
public class QnADomain /*extends UserDomain*/ {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    // PK

//    @ManyToOne
//    @ToString.Exclude
//    @Column(name = "user_id", nullable = false)
//    private Long userId;    // 유저 아이디

    @Column(name = "question" , columnDefinition = "longtext" , nullable = false)
    private String question;        // 질문글

    @Column(name = "answer" , columnDefinition = "longtext" )
    private String answer;      // 답변

    @Column(name = "reg_data" , nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date regDate;       // 질문시간

    @Column(name = "anser_data" , nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date answerDate;       //  답변시간


}
