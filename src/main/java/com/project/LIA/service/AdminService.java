package com.project.LIA.service;

import com.project.LIA.domain.UserDomain;
import org.springframework.ui.Model;

import java.util.List;

public interface AdminService {

    // 회원 리스트
    List<UserDomain> userList(Integer page, Model model);
}
