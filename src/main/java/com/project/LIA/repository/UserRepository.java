package com.project.LIA.repository;

import com.project.LIA.domain.UserDomain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDomain,Long> {
    UserDomain findByUsername(String username);

    UserDomain findByEmail(String email);
}
