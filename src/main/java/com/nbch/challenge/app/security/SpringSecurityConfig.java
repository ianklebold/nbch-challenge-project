package com.nbch.challenge.app.security;

import com.nbch.challenge.app.controllers.ProductoController;
import com.nbch.challenge.app.repository.user.UserRepository;
import com.nbch.challenge.app.security.filters.JwtAuthenticationFilter;
import com.nbch.challenge.app.security.filters.JwtValidationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SpringSecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    private UserRepository userRepository;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requests -> requests
                .requestMatchers(new AntPathRequestMatcher(ProductoController.PRODUCTO_PATH+ProductoController.PATH_ID,HttpMethod.GET.name())).hasAnyAuthority("ADMIN","REGULAR_USER")
                .requestMatchers(new AntPathRequestMatcher(ProductoController.PRODUCTO_PATH,HttpMethod.POST.name())).hasAnyAuthority("ADMIN")
                .requestMatchers(new AntPathRequestMatcher(ProductoController.PRODUCTO_PATH+ProductoController.PATH_ID,HttpMethod.DELETE.name())).hasAnyAuthority("ADMIN")
                .requestMatchers(new AntPathRequestMatcher(ProductoController.PRODUCTO_PATH,HttpMethod.GET.name())).permitAll()
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                .anyRequest().authenticated())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilter(new JwtAuthenticationFilter(this.authenticationConfiguration.getAuthenticationManager(),userRepository))
                .addFilter(new JwtValidationFilter(this.authenticationConfiguration.getAuthenticationManager()));


        return http.build();
    }



}
