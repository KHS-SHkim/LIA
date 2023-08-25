package com.project.LIA.service;

import com.project.LIA.domain.AddressDomain;
import com.project.LIA.domain.UserDomain;
import com.project.LIA.repository.AddressRepository;
import com.project.LIA.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.LIA.util.U;

@Service
public class AddressServiceImpl implements AddressService{

    private AddressRepository addressRepository;

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setAddressRepository(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public int register(AddressDomain addressDomain) {

        addressRepository.saveAndFlush(addressDomain);

        return 1;
    }

    @Override
    public int update(AddressDomain addressDomain) {

        addressRepository.save(addressDomain);

        return 1;
    }

    @Override
    public AddressDomain findByUserId(Long user_id) {

        // 로그인한 유저
        UserDomain userDomain = U.getLoggedUser();

        // DB 에 저장된 유저 가져오기
        userDomain = userRepository.findById(userDomain.getId()).orElse(null);

        user_id = userDomain.getId();

        AddressDomain addressDomain = addressRepository.findByUserId(user_id);

        return addressDomain;

    }
}
