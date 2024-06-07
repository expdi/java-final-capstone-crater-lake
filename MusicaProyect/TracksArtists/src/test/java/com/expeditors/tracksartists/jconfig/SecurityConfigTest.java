package com.expeditors.tracksartists.jconfig;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("h2")
class SecurityConfigTest {

    @Test
    void userDetailsService() throws Exception {
        UserDetails alanaudo = User.withUsername("alanaudo")
                .password("{bcrypt}$2b$12$57hCIjtRdycZER1e36ZuT.OpjScjbTc5JtrEzq1iG.U3BPK3AOXO.")
                .roles("USER")
                .build();

        assertNotNull(alanaudo);
    }

    @Test
    void tracksChain() throws Exception {
//        // Mock HttpSecurity
//        HttpSecurity http = mock(HttpSecurity.class);
//
//        // Define Authorization Rules
//        http.authorizeHttpRequests(auth -> {
//            auth.requestMatchers(HttpMethod.GET, "/api/artist/**").authenticated();
//            auth.requestMatchers(HttpMethod.PUT, "/api/artist/**").hasRole("USER");
//            auth.requestMatchers(HttpMethod.POST, "/api/artist/**").hasRole("USER");
//            auth.requestMatchers(HttpMethod.DELETE, "/api/artist/**").hasRole("USER");
//            auth.requestMatchers(HttpMethod.GET, "/api/track/**").authenticated();
//            auth.requestMatchers(HttpMethod.PUT, "/api/track/**").hasRole("USER");
//            auth.requestMatchers(HttpMethod.POST, "/api/track/**").authenticated();
//            auth.requestMatchers(HttpMethod.DELETE, "/api/track/**").hasRole("USER");
//            auth.anyRequest().denyAll();
//        });
//
//        // Configure security basic with defaults
//        http.httpBasic(Customizer.withDefaults());
//
//        // Disable csrf (be cautious in production)
//        http.csrf(AbstractHttpConfigurer::disable);
//
//        // Verify successful build (optional)
//        assertNotNull(http.build());
    }
}