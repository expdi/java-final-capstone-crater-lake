package com.expeditors.pricing.jconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails andre = User.withUsername("andre")
                .password("{bcrypt}$2a$10$Q7Cw0CPud2FyibAMo0qAWukTMPlVYhU.sBdZMRXec.1GVZdsjVfGu")
                .roles("ADMIN", "USER")
                .build();

        UserDetails waffles = User.withUsername("waffles")
                .password("{bcrypt}$2a$10$fR66BsNci0b1LD5GPf/eJuIshDxlHgT/9XnwxRaGkhi6gw1W3YFoi")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(andre, waffles);
    }

    @Bean
    public SecurityFilterChain pricingFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers(HttpMethod.GET, "/api/pricing/**").authenticated();
            auth.requestMatchers(HttpMethod.PUT).hasRole("ADMIN");

            auth.anyRequest().denyAll();
        });

        http.httpBasic(Customizer.withDefaults());

        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }
}
