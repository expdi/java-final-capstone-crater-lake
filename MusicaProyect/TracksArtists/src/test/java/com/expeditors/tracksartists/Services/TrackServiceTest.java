package com.expeditors.tracksartists.Services;

import com.expeditors.tracksartists.dataAccessObjects.ITrackDao;
import com.expeditors.tracksartists.enums.MediaType;
import com.expeditors.tracksartists.models.Artist;
import com.expeditors.tracksartists.models.Track;
import com.expeditors.tracksartists.services.implemetations.TrackServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class TrackServiceTest {

    @Mock
    private ITrackDao trackDao;

    @InjectMocks
    private TrackServiceImpl trackService;

    @Test
    void add() {
        Artist artist = new Artist();
        artist.setId(1);
        artist.setName("Michael A");

        Track track1 = new Track();
        track1.setTitle("Track 1");
        track1.setEMediaType(MediaType.FLAC);
        track1.setDuration(Duration.ofSeconds(150));
        track1.setAlbum("Albulm #1");
        track1.setIssueDate(LocalDateTime.now());
        track1.getArtists().add(artist);

        Track track2 = new Track();
        track2.setTitle("Track 2");
        track2.setEMediaType(MediaType.MP3);
        track2.setDuration(Duration.ofSeconds(150));
        track2.setAlbum("Albulm #1");
        track2.setIssueDate(LocalDateTime.now());
        track2.getArtists().add(artist);

        Track track3 = new Track();
        track3.setTitle("Track 3");
        track3.setEMediaType(MediaType.OGG);
        track3.setDuration(Duration.ofSeconds(150));
        track3.setAlbum("Albulm #1");
        track3.setIssueDate(LocalDateTime.now());
        track3.getArtists().add(artist);

        Track track4 = new Track();
        track4.setTitle("Track 4");
        track4.setEMediaType(MediaType.WAV);
        track4.setDuration(Duration.ofSeconds(150));
        track4.setAlbum("Albulm #1");
        track4.setIssueDate(LocalDateTime.now());
        track4.getArtists().add(artist);

        when(trackDao.save(any(Track.class))).thenReturn(track1, track2, track3, track4);

        Track result1 = trackService.add(track1);
        Track result2 = trackService.add(track2);
        Track result3 = trackService.add(track3);
        Track result4 = trackService.add(track4);

        assertAll(
                () -> assertNotNull(result1),
                () -> assertEquals(track1.getTitle(), result1.getTitle()),
                () -> assertEquals(track1.getEMediaType(), result1.getEMediaType()),

                () -> assertNotNull(result2),
                () -> assertEquals(track2.getTitle(), result2.getTitle()),
                () -> assertEquals(track2.getEMediaType(), result2.getEMediaType()),

                () -> assertNotNull(result3),
                () -> assertEquals(track3.getTitle(), result3.getTitle()),
                () -> assertEquals(track3.getEMediaType(), result3.getEMediaType()),

                () -> assertNotNull(result4),
                () -> assertEquals(track4.getTitle(), result4.getTitle()),
                () -> assertEquals(track4.getEMediaType(), result4.getEMediaType())
        );

        verify(trackDao, times(4)).save(any(Track.class));
    }

    @Test
    void getById() {
        int trackId = 1;
        Track track = new Track();
        track.setId(trackId);

        when(trackDao.getReferenceById(trackId)).thenReturn(track);

        Track result = trackService.getById(trackId);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(track.getTitle(), result.getTitle()),
                () -> assertEquals(track.getDuration(), result.getDuration())
        );

        verify(trackDao).getReferenceById(trackId);
    }

    @Test
    void update() {
        int trackId = 1;
        Track track = new Track();
        track.setId(trackId);
        track.setTitle("Dance Dance Dance!!!");

        when(trackDao.save(any(Track.class))).thenReturn(track);

        trackService.update(track);

        verify(trackDao).save(any(Track.class));
    }

    @Test
    void delete() {
        int trackId = 1;

        doNothing().when(trackDao).deleteById(trackId);

        trackService.delete(trackId);

        verify(trackDao).deleteById(trackId);
    }

    @Test
    void getAll() {
        List<Track> tracks = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            String title = "Dance Dance Dance " + (i + 1) + "!!!!";
            Track newTrack = new Track();
            newTrack.setId(i+1);
            newTrack.setTitle(title);

            tracks.add(newTrack);
        }

        when(trackDao.findAll()).thenReturn(tracks);

        List<Track> results = trackService.getAll();

        assertAll(
                () -> assertNotNull(results),
                () -> assertEquals(tracks.size(), results.size()),
                () -> assertEquals(tracks.getFirst().getTitle(), results.getFirst().getTitle())
        );
    }

    @Test
    void getAllByMediaType() {
    }

    @Test
    void getAllByIssueDateYear() {
    }

    @Test
    void getArtistByTrack() {
    }

    @Test
    void getByDurationDynamic() {
    }
}