package com.project.LIA.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserVofR {

    private String username;

    private String password;

    @ToString.Exclude
    @JsonIgnore
    private String re_password;

    private String nickname;

    private String phone;

    private String email;

    private String post_num;

    private String address;

    private String address_detail;


}
