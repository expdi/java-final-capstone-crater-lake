package com.expeditors.tracksartists.Services;

import com.expeditors.tracksartists.dataAccessObjects.IArtistDao;
import com.expeditors.tracksartists.dataAccessObjects.ITrackDao;
import com.expeditors.tracksartists.enums.DEvaluation;
import com.expeditors.tracksartists.enums.MediaType;
import com.expeditors.tracksartists.exceptionHandlers.exceptions.WrongRequestException;
import com.expeditors.tracksartists.models.Artist;
import com.expeditors.tracksartists.models.Track;
import com.expeditors.tracksartists.services.implemetations.TrackServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class TrackServiceTest {

    @Mock
    private ITrackDao trackDao;

    @Mock
    private IArtistDao artistDao;

    @InjectMocks
    private TrackServiceImpl trackService;

    @Test
    void add() {
        Artist artist = new Artist();
        artist.setId(1);
        artist.setName("Michael A");

        Track track1 = new Track();
        track1.setTitle("Track 1");
        track1.setMediaType(MediaType.FLAC);
        track1.setDuration(Duration.ofSeconds(150));
        track1.setAlbum("Albulm #1");
        track1.setIssueDate(LocalDateTime.now());
        track1.getArtists().add(artist);

        Track track2 = new Track();
        track2.setTitle("Track 2");
        track2.setMediaType(MediaType.MP3);
        track2.setDuration(Duration.ofSeconds(150));
        track2.setAlbum("Albulm #1");
        track2.setIssueDate(LocalDateTime.now());
        track2.getArtists().add(artist);

        Track track3 = new Track();
        track3.setTitle("Track 3");
        track3.setMediaType(MediaType.OGG);
        track3.setDuration(Duration.ofSeconds(150));
        track3.setAlbum("Albulm #1");
        track3.setIssueDate(LocalDateTime.now());
        track3.getArtists().add(artist);

        Track track4 = new Track();
        track4.setTitle("Track 4");
        track4.setMediaType(MediaType.WAV);
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
                () -> assertEquals(track1.getMediaType(), result1.getMediaType()),

                () -> assertNotNull(result2),
                () -> assertEquals(track2.getTitle(), result2.getTitle()),
                () -> assertEquals(track2.getMediaType(), result2.getMediaType()),

                () -> assertNotNull(result3),
                () -> assertEquals(track3.getTitle(), result3.getTitle()),
                () -> assertEquals(track3.getMediaType(), result3.getMediaType()),

                () -> assertNotNull(result4),
                () -> assertEquals(track4.getTitle(), result4.getTitle()),
                () -> assertEquals(track4.getMediaType(), result4.getMediaType())
        );

        verify(trackDao, times(4)).save(any(Track.class));
    }

    @Test
    void addExistingArtist()
    {
        Artist existingArtist = new Artist();
        existingArtist.setId(1);
        existingArtist.setName("Existing Artist");

        Track track = new Track();
        track.setTitle("Track 4");
        track.setMediaType(MediaType.WAV);
        track.setDuration(Duration.ofSeconds(150));
        track.setAlbum("Albulm #1");
        track.setIssueDate(LocalDateTime.now());
        track.getArtists().add(existingArtist);

        existingArtist.getTracks().add(track);

        when(artistDao.findById(existingArtist.getId())).thenReturn(Optional.of(existingArtist));
        when(trackDao.save(any(Track.class))).thenReturn(track);

        Track result = trackService.add(track);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(track.getTitle(), result.getTitle()),
                () -> assertEquals(track.getMediaType(), result.getMediaType()),
                () -> assertEquals(existingArtist.getName(), Objects.requireNonNull(result.getArtists().stream().findFirst().orElse(null)).getName())
        );

    }

    @Test
    void getById() {
        int trackId = 1;
        Track track = new Track();
        track.setId(trackId);

        when(trackDao.findById(trackId)).thenReturn(Optional.of(track));

        Track result = trackService.getById(trackId);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(track.getTitle(), result.getTitle()),
                () -> assertEquals(track.getDuration(), result.getDuration())
        );

        verify(trackDao).findById(trackId);
    }

    @Test
    void getById_notFound() {
        int trackId = 1;
        String expectedMessage = "Track not found with the specific id";
        int expectedStatusCode = HttpStatus.NOT_FOUND.value();

        when(trackDao.findById(trackId)).thenReturn(Optional.empty());

        WrongRequestException expectedException = assertThrows(WrongRequestException.class, () -> trackService.getById(trackId));

        assertEquals(expectedMessage, expectedException.getMessage());
        assertEquals(expectedStatusCode, expectedException.getHttpStatus().value());

        verify(trackDao).findById(trackId);
    }

    @Test
    void update() {
        int trackId = 1;
        Track track = new Track();
        track.setId(trackId);
        track.setTitle("Dance Dance Dance!!!");

        when(trackDao.existsById(trackId)).thenReturn(true);
        when(trackDao.save(any(Track.class))).thenReturn(track);

        trackService.update(track);

        verify(trackDao).existsById(trackId);
        verify(trackDao).save(any(Track.class));
    }


    @Test
    void updateTrack_noFound() {
        int trackId = 1;
        Track track = new Track();
        track.setId(trackId);

        String expectedMessage = "Track not found with the specific id";
        int expectedStatusCode = HttpStatus.NOT_FOUND.value();

        when(trackDao.existsById(trackId)).thenReturn(false);

        WrongRequestException expectedException = assertThrows(WrongRequestException.class, () -> trackService.update(track));
        assertEquals(expectedMessage, expectedException.getMessage());
        assertEquals(expectedStatusCode, expectedException.getHttpStatus().value());

        verify(trackDao).existsById(trackId);
    }

    @Test
    void delete() {
        int trackId = 1;
        Track track = new Track();
        track.setId(trackId);


        when(trackDao.findById(trackId)).thenReturn(Optional.of(track));
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
            newTrack.setId(i + 1);
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

        verify(trackDao).findAll();
    }

    @Test
    void getAllByMediaTypeFlac() {
        List<Track> tracks = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            String title = "Dance Dance Dance " + (i + 1) + "!!!!";
            Track track = new Track();
            track.setId(i + 1);
            track.setTitle(title);
            track.setMediaType(MediaType.FLAC);
            tracks.add(track);
        }

        when(trackDao.findAllByMediaType(any(MediaType.class))).thenReturn(tracks);

        List<Track> results = trackService.getAllByMediaType(MediaType.FLAC);

        assertAll(
                () -> assertNotNull(results),
                () -> assertEquals(tracks.size(), results.size()),
                () -> assertEquals(tracks.getFirst().getTitle(), results.getFirst().getTitle()),
                () -> assertEquals(tracks.getFirst().getMediaType(), results.getFirst().getMediaType())
        );

        verify(trackDao).findAllByMediaType(any(MediaType.class));
    }

    @Test
    void getAllByMediaTypeMp3() {
        List<Track> tracks = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            String title = "Dance Dance Dance " + (i + 1) + "!!!!";
            Track track = new Track();
            track.setId(i + 1);
            track.setTitle(title);
            track.setMediaType(MediaType.MP3);
            tracks.add(track);
        }

        when(trackDao.findAllByMediaType(any(MediaType.class))).thenReturn(tracks);

        List<Track> results = trackService.getAllByMediaType(MediaType.MP3);

        assertAll(
                () -> assertNotNull(results),
                () -> assertEquals(tracks.size(), results.size()),
                () -> assertEquals(tracks.getFirst().getTitle(), results.getFirst().getTitle()),
                () -> assertEquals(tracks.getFirst().getMediaType(), results.getFirst().getMediaType())
        );

        verify(trackDao).findAllByMediaType(any(MediaType.class));
    }

    @Test
    void getAllByMediaTypeOgg() {
        List<Track> tracks = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            String title = "Dance Dance Dance " + (i + 1) + "!!!!";
            Track track = new Track();
            track.setId(i + 1);
            track.setTitle(title);
            track.setMediaType(MediaType.OGG);
            tracks.add(track);
        }

        when(trackDao.findAllByMediaType(any(MediaType.class))).thenReturn(tracks);

        List<Track> results = trackService.getAllByMediaType(MediaType.OGG);

        assertAll(
                () -> assertNotNull(results),
                () -> assertEquals(tracks.size(), results.size()),
                () -> assertEquals(tracks.getFirst().getTitle(), results.getFirst().getTitle()),
                () -> assertEquals(tracks.getFirst().getMediaType(), results.getFirst().getMediaType())
        );

        verify(trackDao).findAllByMediaType(any(MediaType.class));
    }


    @Test
    void getAllByMediaTypeWav() {
        List<Track> tracks = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            String title = "Dance Dance Dance " + (i + 1) + "!!!!";
            Track track = new Track();
            track.setId(i + 1);
            track.setTitle(title);
            track.setMediaType(MediaType.WAV);
            tracks.add(track);
        }

        when(trackDao.findAllByMediaType(any(MediaType.class))).thenReturn(tracks);

        List<Track> results = trackService.getAllByMediaType(MediaType.WAV);

        assertAll(
                () -> assertNotNull(results),
                () -> assertEquals(tracks.size(), results.size()),
                () -> assertEquals(tracks.getFirst().getTitle(), results.getFirst().getTitle()),
                () -> assertEquals(tracks.getFirst().getMediaType(), results.getFirst().getMediaType())
        );

        verify(trackDao).findAllByMediaType(any(MediaType.class));
    }

    @Test
    void getAllByIssueDateYear() {
        LocalDateTime issueDate = LocalDateTime.of(2022,5,6,12,0,0);

        List<Track> tracks = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            String title = "Dance Dance Dance " + (i + 1) + "!!!!";
            Track track = new Track();
            track.setId(i + 1);
            track.setTitle(title);
            track.setMediaType(MediaType.WAV);
            track.setIssueDate(issueDate);
            tracks.add(track);
        }

        when(trackDao.findAllByIssueDate(issueDate.getYear())).thenReturn(tracks);

        List<Track> results = trackService.getAllByIssueDateYear(issueDate.getYear());

        assertAll(
                () -> assertNotNull(results),
                () -> assertEquals(tracks.size(), results.size()),
                () -> assertEquals(tracks.getFirst().getTitle(), results.getFirst().getTitle()),
                () -> assertEquals(issueDate.getYear(), results.getFirst().getIssueDate().getYear())
        );

        verify(trackDao).findAllByIssueDate(issueDate.getYear());
    }

    @Test
    void getArtistByTrack() {
        Track track = new Track();
        track.setId(1);
        track.setTitle("Track #1");
        track.setMediaType(MediaType.WAV);

        for (int i = 0; i < 2; i++) {
            Artist artist = new Artist();
            artist.setId(i+1);
            artist.setName("John " + i);
            artist.getTracks().add(track);
            track.getArtists().add(artist);
        }

        when(trackDao.findArtistsByTrack(track.getId())).thenReturn(track.getArtists().stream().toList());

        List<Artist> result = trackService.getArtistByTrack(track.getId());

        assertAll(
                () -> assertEquals(track.getArtists().size(), result.size()),
                () -> assertEquals(track.getArtists().stream().toList(), result)
        );

        verify(trackDao).findArtistsByTrack(track.getId());
    }

    @Test
    void getByDurationDynamicEqual() {
        List<Track> tracks = new ArrayList<>();
        int seconds = 400;
        Duration duration = Duration.ofSeconds(seconds);
        DEvaluation evaluation =  DEvaluation.EQUAL;

        for (int i = 0; i < 5; i++) {
            String title = "Dance Dance Dance " + (i + 1) + "!!!!";
            Track track = new Track();
            track.setId(i + 1);
            track.setTitle(title);
            track.setDuration(duration);
            tracks.add(track);
        }

        when(trackDao.findByDurationEquals(duration)).thenReturn(tracks);

        List<Track> results = trackService.getByDurationDynamic(seconds, evaluation);

        assertAll(
                () -> assertNotNull(results),
                () -> assertEquals(tracks.size(), results.size()),
                () -> assertEquals(tracks.getFirst().getTitle(), results.getFirst().getTitle()),
                () -> assertEquals(duration, results.getFirst().getDuration())
        );

        verify(trackDao).findByDurationEquals(duration);
        verify(trackDao, never()).findByDurationLessThan(duration);
        verify(trackDao, never()).findByDurationGreaterThan(duration);
    }

    @Test
    void getByDurationDynamicEqualShorted() {
        List<Track> tracks = new ArrayList<>();
        int secondsFilter = 500;
        Duration durationFilter = Duration.ofSeconds(secondsFilter);
        DEvaluation evaluationFilter =  DEvaluation.SHORTER;
        int trackSeconds = 300;
        Duration trackDuration = Duration.ofSeconds(trackSeconds);

        for (int i = 0; i < 5; i++) {
            String title = "Dance Dance Dance " + (i + 1) + "!!!!";
            Track track = new Track();
            track.setId(i + 1);
            track.setTitle(title);
            track.setDuration(trackDuration);
            tracks.add(track);
        }

        when(trackDao.findByDurationLessThan(durationFilter)).thenReturn(tracks);

        List<Track> results = trackService.getByDurationDynamic(secondsFilter, evaluationFilter);

        assertAll(
                () -> assertNotNull(results),
                () -> assertEquals(tracks.size(), results.size()),
                () -> assertEquals(tracks.getFirst().getTitle(), results.getFirst().getTitle()),
                () -> assertEquals(trackDuration, results.getFirst().getDuration())
        );

        verify(trackDao, never()).findByDurationEquals(durationFilter);
        verify(trackDao).findByDurationLessThan(durationFilter);
        verify(trackDao, never()).findByDurationGreaterThan(durationFilter);
    }

    @Test
    void getByDurationDynamicEqualLonger() {
        List<Track> tracks = new ArrayList<>();
        int secondsFilter = 500;
        Duration durationFilter = Duration.ofSeconds(secondsFilter);
        DEvaluation evaluationFilter =  DEvaluation.LONGER;
        int trackSeconds = 700;
        Duration trackDuration = Duration.ofSeconds(trackSeconds);

        for (int i = 0; i < 5; i++) {
            String title = "Dance Dance Dance " + (i + 1) + "!!!!";
            Track track = new Track();
            track.setId(i + 1);
            track.setTitle(title);
            track.setDuration(trackDuration);
            tracks.add(track);
        }

        when(trackDao.findByDurationGreaterThan(durationFilter)).thenReturn(tracks);

        List<Track> results = trackService.getByDurationDynamic(secondsFilter, evaluationFilter);

        assertAll(
                () -> assertNotNull(results),
                () -> assertEquals(tracks.size(), results.size()),
                () -> assertEquals(tracks.getFirst().getTitle(), results.getFirst().getTitle()),
                () -> assertEquals(trackDuration, results.getFirst().getDuration())
        );

        verify(trackDao, never()).findByDurationEquals(durationFilter);
        verify(trackDao, never()).findByDurationLessThan(durationFilter);
        verify(trackDao).findByDurationGreaterThan(durationFilter);
    }
}