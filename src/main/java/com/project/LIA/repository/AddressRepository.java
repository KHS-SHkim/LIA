package com.project.LIA.repository;

import com.project.LIA.domain.AddressDomain;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<AddressDomain,Long> {
    AddressDomain findByUserId(Long user_id);
}
