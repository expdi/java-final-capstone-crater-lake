package com.expeditors.tracksartists.dataAccessObjects;

import com.expeditors.tracksartists.enums.MediaType;
import com.expeditors.tracksartists.models.Artist;
import com.expeditors.tracksartists.models.Track;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("h2")
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class IArtistDaoH2Test {
    @Autowired
    private IArtistDao artistDao;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
        Artist artist = new Artist();
        artist.setName("John");
        entityManager.persist(artist);

        for (int i = 1; i < 10; i++) {
            Artist newArtist = new Artist();
            newArtist.setName("Team X #" + i );
            entityManager.persist(newArtist);
        }

        for (int i = 0; i <= 2; i++) {
            Track track = new Track();
            track.setTitle("Track #" + (i + 1));
            track.setMediaType(MediaType.FLAC);
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
        assertEquals(artists.size(), artistDao.findAll().size());
    }

    @Test
    void getByIdIn() {
        List<Artist> artists = artistDao.getByIdIn(List.of(1,5));
        assertEquals(2, artists.size());
    }

    @Test
    void getByNameContaining() {
        List<Artist> artists = artistDao.getByNameContaining("Team X");
        assertEquals(9, artists.size());
    }
}