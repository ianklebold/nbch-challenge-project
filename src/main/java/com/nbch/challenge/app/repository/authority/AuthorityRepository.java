package com.nbch.challenge.app.repository.authority;

import com.nbch.challenge.app.domain.Authority;
import com.nbch.challenge.app.domain.enumeration.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority,Long> {
    Optional<Authority> findAuthorityByRoles(Roles roles);
}
