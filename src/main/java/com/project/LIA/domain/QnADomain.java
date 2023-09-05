package com.project.LIA.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@DynamicUpdate
@DynamicInsert
@Entity(name = "qna")
public class QnADomain extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    // PK

    @Column(name = "title", nullable = false)
    private String title;   // 제목

    @Column(name = "question" , columnDefinition = "longtext" , nullable = false)
    private String question;        // 질문글

    @Column(name = "answer" , columnDefinition = "longtext" )
    private String answer;      // 답변

    private LocalDateTime answerDate;       //  답변시간

    @ManyToOne
    @ToString.Exclude
    private UserDomain user;

}