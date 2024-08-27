package com.nbch.challenge.app.seeds;

import com.nbch.challenge.app.domain.Authority;
import com.nbch.challenge.app.domain.User;
import com.nbch.challenge.app.domain.enumeration.Roles;
import com.nbch.challenge.app.repository.authority.AuthorityRepository;
import com.nbch.challenge.app.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;


@Component
@RequiredArgsConstructor
@Slf4j
public class SeedData implements CommandLineRunner {
    private final AuthorityRepository authorityRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private static final String PASSWORD_TEMPLATE = "NBCH_PASS_TEMPLATE";

    @Override
    public void run(String... args) throws Exception {
        log.info("Seeding data...");


        loadAuthorityData();
        loadAdminAndEnterprises();

        log.info("Seeding data complete.");
    }

    private void loadAdminAndEnterprises(){
        if(userRepository.count() < 2){
            User user = new User();
            user.setUsername("ADMIN_NBCH");
            user.setPassword(passwordEncoder.encode(PASSWORD_TEMPLATE));
            user.setAuthorities(List.of(authorityRepository.findAuthorityByRoles(Roles.ADMIN).get()));
            userRepository.save(user);

            User user2 = new User();
            user2.setUsername("REGULAR_USER_NBCH");
            user2.setPassword(passwordEncoder.encode(PASSWORD_TEMPLATE));
            user2.setAuthorities(List.of(authorityRepository.findAuthorityByRoles(Roles.REGULAR_USER).get()));
            userRepository.save(user2);
        }
    }

    private void loadAuthorityData() {
        if (authorityRepository.count() < 2) {
            Authority adminAuthority = Authority.builder()
                    .roles(Roles.ADMIN)
                    .users(Collections.emptyList())
                    .build();

            Authority regularUserAuthority = Authority.builder()
                    .roles(Roles.REGULAR_USER)
                    .users(Collections.emptyList())
                    .build();

            authorityRepository.save(regularUserAuthority);
            authorityRepository.save(adminAuthority);
        }
    }
}
