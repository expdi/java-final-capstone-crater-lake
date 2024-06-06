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
                .password("{bcrypt}$2a$10$TPWu.4BJPkBEuppRAkuqMOt2JoIPdruNb7TT/UkZLT/Cfelh6DIuy")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(alanaudo);
    }

    @Bean
    public SecurityFilterChain courseRatingChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers(HttpMethod.GET, "/artist/**").authenticated();
            auth.requestMatchers(HttpMethod.PUT, "/artist/**").hasRole("USER");
            auth.requestMatchers(HttpMethod.POST, "/artist/**").hasRole("USER");
            auth.anyRequest().denyAll();
        });

        http.httpBasic(Customizer.withDefaults());

        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }
}
