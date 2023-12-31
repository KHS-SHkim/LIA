package com.project.LIA.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@DynamicUpdate
@Entity(name = "user")
public class UserDomain extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @ToString.Exclude
    @JsonIgnore
    @Transient
    private String re_password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    private String profile_img;

    private String state;

    @ToString.Exclude
    @Builder.Default
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    private List<AuthorityDomain> authorities = new ArrayList<>();

    public void addAuthority(AuthorityDomain... authorityDomains){
        if(authorityDomains != null){
            Collections.addAll(this.authorities, authorityDomains);
        }
    }

}
