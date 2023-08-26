package com.project.LIA.service;

import com.project.LIA.domain.AddressDomain;

public interface AddressService {

    // 주소등록
    int register(AddressDomain addressDomain);

    // 회원 PK 로 주소 찾기
    AddressDomain findByUserId(Long user_id);

    // 주소 수정
    int update(AddressDomain addressDomain);



}
