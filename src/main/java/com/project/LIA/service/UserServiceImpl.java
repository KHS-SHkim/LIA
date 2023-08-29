package com.project.LIA.service;

import com.project.LIA.domain.AddressDomain;
import com.project.LIA.domain.AuthorityDomain;
import com.project.LIA.domain.UserDomain;
import com.project.LIA.repository.AddressRepository;
import com.project.LIA.repository.AuthorityRepository;
import com.project.LIA.repository.UserRepository;
import com.project.LIA.util.U;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Value("${app.upload.path}")
    private String uploadDir;

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



        if(isDelete == 1){
            delFile(originalImage);
            user.setProfile_img(null);
        } else if(isDelete == 0){
            delFile(originalImage);
            String originalFilename = multipartFile.getOriginalFilename();

            if(originalFilename == null || originalFilename.length()==0){
                user.setProfile_img(null);
                userRepository.save(user);
                return 1;
            }

            String sourceName = StringUtils.cleanPath(originalFilename);

            String fileName = sourceName;

            File file1 = new File(uploadDir + File.separator + sourceName);
            if(file1.exists()) {
                int pos = fileName.lastIndexOf(".");
                if(pos > -1 ){
                    String name =fileName.substring(0, pos);
                    String ext = fileName.substring(pos + 1);

                    fileName = name + "_" + System.currentTimeMillis() + "." + ext;
                } else {
                    fileName += "_" + System.currentTimeMillis();
                }
            }

            user.setProfile_img(fileName);

            Path copyOfLocation = Paths.get(new File(uploadDir + File.separator + fileName).getAbsolutePath());

            try {
                Files.copy(
                        multipartFile.getInputStream(),
                        copyOfLocation,
                        StandardCopyOption.REPLACE_EXISTING
                );
            } catch (IOException e){
                e.printStackTrace();
            }
            userRepository.save(user);
            return 1;
        }
        userRepository.save(user);
        return 1;
    }

    private void delFile(String originalImage) {
        String saveDirectory = new File(uploadDir).getAbsolutePath();

        File file = new File(saveDirectory, originalImage);
        file.delete();
    }


    @Override
    public List<AuthorityDomain> selectAuthoritiesById(long id) {
        UserDomain userDomain = userRepository.findById(id).orElse(null);

        if(userDomain != null) return userDomain.getAuthorities();

        return new ArrayList<>();
    }

    @Override
    public UserDomain findById(Long user_id) {
        return userRepository.findById(user_id).orElse(null);
    }
}
