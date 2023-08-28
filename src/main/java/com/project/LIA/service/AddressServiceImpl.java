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
    public void setAddressRepository(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public int register(AddressDomain addressDomain) {
        addressRepository.saveAndFlush(addressDomain);
        return 1;
    }

    @Override
    public AddressDomain findByUserId(Long user_id) {
        UserDomain user = U.getLoggedUser();

        user = userRepository.findById(user.getId()).orElse(null);
        user_id = user.getId();
        AddressDomain addressDomain = addressRepository.findByUserId(user_id);
        return addressDomain;
    }

    @Override
    public int update(AddressDomain addressDomain) {
        addressRepository.saveAndFlush(addressDomain);
        return 1;
    }
}
