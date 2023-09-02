package com.project.LIA.service;

import com.project.LIA.domain.UserDomain;
import com.project.LIA.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService{

    UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public List<UserDomain> userList() {
        return userRepository.findAll();
    }


}
