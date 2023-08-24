package com.project.LIA.service;

import com.project.LIA.domain.AuthorityDomain;
import com.project.LIA.domain.UserDomain;
import com.project.LIA.repository.AuthorityRepository;
import com.project.LIA.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

        AuthorityDomain auth = authorityRepository.findByName("ROLE_MEMBER");
        userDomain.addAuthority(auth);

        userDomain = userRepository.saveAndFlush(userDomain);

        System.out.println();

        return 1;
    }

    @Override
    public int update(Integer isDelete, String originalImage, UserDomain user, MultipartFile multipartFile) {
        return 0;
    }


    @Override
    public List<AuthorityDomain> selectAuthoritiesById(long id) {
        return null;
    }
}
