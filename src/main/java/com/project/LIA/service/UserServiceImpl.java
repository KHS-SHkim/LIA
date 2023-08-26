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

import java.util.ArrayList;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(){}


    @Override
    public UserDomain findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean isExist(String username) {
        UserDomain userDomain = findByUsername(username);
        return (userDomain != null) ? true : false;
    }

    @Override
    public boolean isEmail(String email) {
        UserDomain userDomain = userRepository.findByEmail(email);
        return (userDomain != null)? true : false;
    }

    @Override
    public int register(UserDomain userDomain) {

        userDomain.setPassword(passwordEncoder.encode(userDomain.getPassword()));
        userDomain.addAuthority(authorityRepository.findByName("ROLE_MEMBER"));

        userRepository.saveAndFlush(userDomain);

        return 1;
    }


    @Override
    public List<AuthorityDomain> selectAuthoritiesById(long id) {
        UserDomain userDomain = userRepository.findById(id).orElse(null);

        if(userDomain != null) return userDomain.getAuthorities();
        return new ArrayList<>();
    }
}
