package com.crudProject.crud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
public class SecurityLayer {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.
                csrf(custom -> custom.disable())
                .authorizeHttpRequests(authorize -> authorize.requestMatchers
                        ("/api/auth/**","/api/public/**").
                        permitAll().requestMatchers("/api/admin/**").
                        hasRole("ADMIN").requestMatchers("/api/user/**").hasRole("USER").anyRequest().authenticated()).
                            httpBasic(Customizer.withDefaults()).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails userDetails = User.builder().username("danuj").password(passwordEncoder.encode("123")).roles("USER").build();
        UserDetails adminDetails = User.builder().username("admin").password(passwordEncoder.encode("admin@123")).roles("ADMIN").build();
            return new InMemoryUserDetailsManager(userDetails,adminDetails);
    }

}

