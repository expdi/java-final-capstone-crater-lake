package com.expeditors.tracksartists;

import com.expeditors.tracksartists.controllers.*;
import com.expeditors.tracksartists.services.implemetations.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hibernate.validator.internal.util.Contracts.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TracksArtistsApplicationTest {

    @Test
    public void contextLoads() throws Exception {

    }
}