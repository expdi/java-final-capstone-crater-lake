package com.expeditors.tracksartists.dataAccessObjects;

import com.expeditors.tracksartists.enums.MediaType;
import com.expeditors.tracksartists.services.implemetations.models.Artist;
import com.expeditors.tracksartists.services.implemetations.models.Track;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("h2")
@DataJpaTest
class IArtistDaoTest {
    @Autowired
    private IArtistDao artistDao;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
        Artist artist = new Artist();
        artist.setName("John");
        entityManager.persist(artist);

        for (int i = 0; i < 5; i++) {
            Track track = new Track();
            track.setTitle("Track #" + (i + 1));
            track.setEMediaType(MediaType.FLAC);
            track.setDuration(Duration.ofSeconds(150));
            track.setAlbum("Album #1");
            track.setIssueDate(LocalDateTime.now());
            track.getArtists().add(artist);
            artist.getTracks().add(track);

            entityManager.persist(track);
        }

        entityManager.persist(artist);
    }

    @Test
    void save() {
        Artist artist = new Artist();
        artist.setName("John");

        Artist result = artistDao.save(artist);

        assertTrue(result.getId() != 0);
        assertEquals(artist.getName(), result.getName());
    }

    @Test
    void delete() {

        List<Artist> artists = artistDao.findAll();

        artistDao.deleteById(1);

        assertEquals(artists.size()-1, artistDao.findAll().size());
    }

    @Test
    void getByIdIn() {
    }

    @Test
    void getByNameContaining() {
    }
}