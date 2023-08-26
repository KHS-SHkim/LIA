package com.project.LIA.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

// Validation 을 위한 객체
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
    private String profile_img;
    private String address;
    private String address_detail;
    private String post_num;
}
