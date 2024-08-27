package com.nbch.challenge.app.service.user;

import com.nbch.challenge.app.domain.User;
import com.nbch.challenge.app.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()){
            throw new UsernameNotFoundException(String.format("Usuario incorrecto"));
        }

        User userExist = user.get();

        List<GrantedAuthority> authorities = new ArrayList<>(
                userExist.getAuthorities()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority(role.getRoles().toString()))
                        .toList()
        );

        return new org.springframework.security.core.userdetails.User(
                userExist.getUsername(),
                userExist.getPassword(),
                true,
                true,
                true,
                true,
                authorities
        );
    }

}
