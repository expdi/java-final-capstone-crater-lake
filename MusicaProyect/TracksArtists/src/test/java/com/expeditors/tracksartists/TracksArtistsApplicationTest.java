package com.expeditors.tracksartists;

import com.expeditors.tracksartists.controllers.*;
import com.expeditors.tracksartists.services.implemetations.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hibernate.validator.internal.util.Contracts.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TracksArtistsApplicationTest {
    @Autowired
    private ArtistController artistController;

    @Autowired
    private TrackController trackController;

    @Autowired
    private ArtistServiceImpl artistService;

    @Autowired
    private TrackServiceImpl trackService;

    @Test
    public void contextLoads() throws Exception {
        assertNotNull(artistController);
        assertNotNull(trackController);
        assertNotNull(artistService);
        assertNotNull(trackService);
    }
}