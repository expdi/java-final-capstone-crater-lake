package com.expeditors.tracksartists.jconfig;

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

        UserDetails alanaudo = User.withUsername("alanaudo")
                .password("{bcrypt}$2a$10$LkAQVsjdRLqd/s/DxB6qg.PTCtpFzeaU6bq6NpaMRA.GuldBfOnQa")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(alanaudo);
    }

    @Bean
    public SecurityFilterChain tracksChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers(HttpMethod.GET, "/api/artist/**").authenticated();
            auth.requestMatchers(HttpMethod.PUT, "/api/artist/**").hasRole("USER");
            auth.requestMatchers(HttpMethod.POST, "/api/artist/**").hasRole("USER");
            auth.requestMatchers(HttpMethod.DELETE, "/api/artist/**").hasRole("USER");
            auth.requestMatchers(HttpMethod.GET, "/api/track/**").authenticated();
            auth.requestMatchers(HttpMethod.PUT, "/api/track/**").hasRole("USER");
            auth.requestMatchers(HttpMethod.POST, "/api/track/**").authenticated();
            auth.requestMatchers(HttpMethod.DELETE, "/api/track/**").hasRole("USER");
            auth.anyRequest().denyAll();
        });

        http.httpBasic(Customizer.withDefaults());

        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }
}
