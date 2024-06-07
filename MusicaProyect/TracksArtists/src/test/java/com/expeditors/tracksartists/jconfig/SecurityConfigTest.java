package com.expeditors.tracksartists.jconfig;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
    }
}