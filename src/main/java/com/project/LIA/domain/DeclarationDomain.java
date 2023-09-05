package com.project.LIA.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=false)
@Entity(name = "declaration")
public class DeclarationDomain extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Note : User = N:1
    @ManyToOne(fetch = FetchType.LAZY, targetEntity= UserDomain.class )
    @ToString.Exclude
    @JoinColumn(name="user_id", referencedColumnName = "id")
    @JsonIgnore
    private UserDomain user;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity= UserDomain.class )
    @ToString.Exclude
    @JoinColumn(name="reporter_id", referencedColumnName = "id")
    @JsonIgnore
    private UserDomain reporter;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity= BookDomain.class )
    @ToString.Exclude
    @JoinColumn(name="book_id", referencedColumnName = "id")
    @JsonIgnore
    private BookDomain book;


    private String reportType;


    @Column(name = "report_content", columnDefinition = "LONGTEXT")
    private String reportContent;


    private String answerContent;

    @CreatedDate   // Entity 생성시점의 시간 입력
    // java.time.*  객체 변환을 위한 annotation
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @JsonProperty("answerdate")
    private LocalDateTime answerDate;


    @Transient
    String userNickname;

    @Transient
    String receiverNickname;

    @Transient
    Long bookInfo;

    @Transient
    String bookName;

}
