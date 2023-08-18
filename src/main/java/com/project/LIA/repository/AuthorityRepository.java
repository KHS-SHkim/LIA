package com.project.LIA.repository;

import com.project.LIA.domain.AuthorityDomain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<AuthorityDomain,Long> {
}
