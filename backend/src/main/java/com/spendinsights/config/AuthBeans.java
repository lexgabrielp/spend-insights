package com.spendinsights.config;

import com.spendinsights.repository.UserRepository;
import com.spendinsights.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.*;
import org.springframework.security.core.userdetails.*;

@Configuration
@RequiredArgsConstructor
public class AuthBeans {
    private final UserRepository users;

    @Bean
    UserDetailsService userDetailsService() {
        return email -> users.findByEmail(email).map(UserPrincipal::new).orElseThrow(() -> new UsernameNotFoundException(email));
    }
}
