package io.split.examples.betatestexample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    @Override
    public UserDetailsService userDetailsService() {

        InMemoryUserDetailsManager userManager = new InMemoryUserDetailsManager(
            newUser("linda", "12345678", "USER"),
            newUser("bob", "12345678", "USER"),
            newUser("tina", "12345678", "USER", "BETA_TESTER"),
            newUser("gene", "12345678", "USER", "BETA_TESTER"),
            newUser("louise", "12345678", "USER", "BETA_TESTER")
        );

        return userManager;
    }

    private UserDetails newUser(String username, String password, String... roles) {
        return User
            .withUsername(username)
            .passwordEncoder(
                PasswordEncoderFactories.createDelegatingPasswordEncoder()::encode
            )
            .password(password).roles(roles).build();
    }
}