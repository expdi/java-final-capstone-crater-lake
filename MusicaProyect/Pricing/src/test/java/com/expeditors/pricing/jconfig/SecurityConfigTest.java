package com.expeditors.pricing.jconfig;

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
    void userDetailsService() {
        UserDetails andre = User.withUsername("andre")
                .password("{bcrypt}$2a$10$Q7Cw0CPud2FyibAMo0qAWukTMPlVYhU.sBdZMRXec.1GVZdsjVfGu")
                .roles("ADMIN", "USER")
                .build();

        UserDetails waffles = User.withUsername("waffles")
                .password("{bcrypt}$2a$10$fR66BsNci0b1LD5GPf/eJuIshDxlHgT/9XnwxRaGkhi6gw1W3YFoi")
                .roles("USER")
                .build();

        assertNotNull(andre);
        assertNotNull(waffles);
    }

    void tracksChain() throws Exception {
    }
}