package com.project.LIA.service;

import com.project.LIA.domain.AuthorityDomain;
import com.project.LIA.domain.UserDomain;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    // 로그인 아이디로 회원 정보 가져오기
    UserDomain findByUsername(String username);

    // 아이디 중복 확인
    boolean isExist(String username);

    // 이메일 중복 확인
    boolean isEmail(String email);

    // 회원 등록
    int register(UserDomain userDomain);

    // 회원 수정
    int update(Integer isDelete, String originalImage, UserDomain user, MultipartFile multipartFile);

    // 비번변경
    int updatePw(UserDomain userDomain);

    // 사용자의 권한들
    List<AuthorityDomain> selectAuthoritiesById(long id);
}
