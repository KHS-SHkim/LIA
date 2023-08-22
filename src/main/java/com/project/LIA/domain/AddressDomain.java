package com.project.LIA.domain;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity(name = "address")
public class AddressDomain extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String address_detail;

    @Column(nullable = false)
    private String post_num;

    private String stat;

    @ManyToOne
    @ToString.Exclude
    private UserDomain userDomain;



}
