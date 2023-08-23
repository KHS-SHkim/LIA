package com.project.LIA.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Entity(name = "qna")
public class QnADomain extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    // PK

    @ManyToOne
    @JoinColumn
    private UserDomain user;

    @Column(name = "question" , columnDefinition = "longtext" , nullable = false)
    private String question;        // 질문글

    @Column(name = "answer" , columnDefinition = "longtext" )
    private String answer;      // 답변

    @Column(name = "anser_data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date answerDate;       //  답변시간


}
