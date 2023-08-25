package com.project.LIA.repository;

import com.project.LIA.domain.AddressDomain;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<AddressDomain,Long> {

    // 유저아이디로 주소 가져오기
    AddressDomain findByUserId(Long user_id);
}
