package com.project.LIA.service;

import com.project.LIA.domain.AddressDomain;

public interface AddressService {

    // 주소 추가
    int register (AddressDomain addressDomain);

    // 주소 수정
    int update (AddressDomain addressDomain);

    // 유저의 주소 가져오기
    AddressDomain findByUserId(Long user_id);
}
