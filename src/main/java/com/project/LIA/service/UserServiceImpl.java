package com.project.LIA.service;

import com.project.LIA.domain.AddressDomain;
import com.project.LIA.domain.AuthorityDomain;
import com.project.LIA.domain.UserDomain;
import com.project.LIA.repository.AuthorityRepository;
import com.project.LIA.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

//    @Value("${app.upload.path}")

    private UserRepository userRepository;

    private AuthorityRepository authorityRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setAuthorityRepository(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(){}


    @Override
    public UserDomain fondByUsername(String username) {
        return null;
    }

    @Override
    public boolean isExist(String username) {
        return false;
    }

    @Override
    public int register(UserDomain userDomain) {
        return 0;
    }

    @Override
    public int addAddress(AddressDomain addressDomain) {
        return 0;
    }

    @Override
    public List<AddressDomain> findAddressByUserId(UserDomain userDomain) {
        return null;
    }

    @Override
    public List<AuthorityDomain> selectAuthoritiesById(long id) {
        return null;
    }
}
