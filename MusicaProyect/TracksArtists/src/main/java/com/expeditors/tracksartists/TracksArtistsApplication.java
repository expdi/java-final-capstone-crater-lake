package com.expeditors.tracksartists;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@SpringBootApplication
public class TracksArtistsApplication {

    public static void main(String[] args) {
        SpringApplication.run(TracksArtistsApplication.class, args);
    }

}
