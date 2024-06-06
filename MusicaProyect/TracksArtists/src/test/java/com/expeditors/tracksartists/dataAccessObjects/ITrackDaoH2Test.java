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
class ITrackDaoH2Test {
    @Autowired
    private ITrackDao trackDao;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {

        for (int i = 1; i <= 5; i++) {
            Artist artist = new Artist();
            artist.setName("Team #" + i );
            entityManager.persist(artist);

            for (int j = 1; j <= 4; j++) {
                Track track = new Track();
                track.setTitle("Track #" + (i + 1));

                track.setDuration(Duration.ofSeconds(j * 100));

                track.setMediaType(
                        switch (j) {
                            case 1 -> MediaType.FLAC;
                            case 2 -> MediaType.OGG;
                            case 3 -> MediaType.WAV;
                            default -> MediaType.MP3;
                        }
                );

                track.setAlbum("Album #1");
                track.setIssueDate(LocalDateTime.now());
                track.getArtists().add(artist);
                artist.getTracks().add(track);

                entityManager.persist(track);
            }

            entityManager.persist(artist);
        }
    }

    @Test
    void findAll() {
        List<Track> tracks = trackDao.findAll();

        assertFalse(tracks.isEmpty());
    }

    @Test
    void findAllByMediaType() {
        List<Track> tracksFlac = trackDao.findAllByMediaType(MediaType.FLAC);
        List<Track> tracksOgg = trackDao.findAllByMediaType(MediaType.OGG);
        List<Track> tracksWav = trackDao.findAllByMediaType(MediaType.WAV);
        List<Track> tracksMp3 = trackDao.findAllByMediaType(MediaType.MP3);

        assertFalse(tracksFlac.isEmpty());
        assertFalse(tracksOgg.isEmpty());
        assertFalse(tracksWav.isEmpty());
        assertFalse(tracksMp3.isEmpty());
    }

    @Test
    void findAllByIssueDate() {
        List<Track> tracks = trackDao.findAllByIssueDate(LocalDateTime.now().getYear());

        assertEquals(20, tracks.size());
    }

    @Test
    void getAllTracksByArtistId() {
        List<Track> tracks = trackDao.getAllTracksByArtistId(1);
        assertEquals(4, tracks.size());
    }

    @Test
    void findArtistsByTrack() {
        List<Artist> artists = trackDao.findArtistsByTrack(1);
        assertEquals(1, artists.size());
    }

    @Test
    void findByDurationLessThan() {
        Duration duration = Duration.ofSeconds(200);
        List<Track> tracksLess = trackDao.findByDurationLessThan(duration);

        assertEquals(5, tracksLess.size());
    }

    @Test
    void findByDurationEquals() {
        Duration duration = Duration.ofSeconds(200);
        List<Track> tracksEquals = trackDao.findByDurationEquals(duration);
        assertEquals(5, tracksEquals.size());
    }

    @Test
    void findByDurationGreaterThan() {
        Duration duration = Duration.ofSeconds(200);
        List<Track> tracksGreater = trackDao.findByDurationGreaterThan(duration);
        assertEquals(10, tracksGreater.size());
    }

    @Test
    void save() {
        Track track = new Track();
        track.setTitle("Track #1000");

        Track result = trackDao.save(track);

        assertAll(
                () -> assertTrue(result.getId() != 0),
                () -> assertEquals(track.getTitle(), result.getTitle())
        );
    }

    @Test
    void delete() {
        int trackId = 1;
        List<Track> tracks = trackDao.findAll();
        trackDao.deleteById(trackId);

        assertAll(
                () -> assertEquals(tracks.size() - 1, trackDao.findAll().size()),
                () -> assertFalse(trackDao.findById(trackId).isPresent())
        );
    }
}