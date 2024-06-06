package com.expeditors.tracksartists.Services;

import com.expeditors.tracksartists.dataAccessObjects.IArtistDao;
import com.expeditors.tracksartists.dataAccessObjects.ITrackDao;
import com.expeditors.tracksartists.exceptionHandlers.exceptions.WrongRequestException;
import com.expeditors.tracksartists.models.Artist;
import com.expeditors.tracksartists.models.Track;
import com.expeditors.tracksartists.services.implemetations.ArtistServiceImpl;
import com.expeditors.tracksartists.services.implemetations.TrackServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ArtistServiceTest {
    @Mock
    private IArtistDao artistDao;

    @Mock
    private ITrackDao trackDao;

    @InjectMocks
    private ArtistServiceImpl artistService;

    @Test
    void add() {
        Artist artist = new Artist();
        artist.setName("Michael A");

        List<Track> tracks = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            String title = "Dance Dance Dance " + (i + 1) + "!!!";
            Track newTrack = new Track();
            newTrack.setId(i+1);
            newTrack.setTitle(title);
            tracks.add(newTrack);
        }

        artist.getTracks().addAll(tracks);

        when(artistDao.save(artist)).thenReturn(artist);

        Artist result = artistService.add(artist);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(artist.getName(), result.getName()),
                () -> assertEquals(artist.getTracks().size(), result.getTracks().size())
        );

        verify(artistDao).save(artist);
    }

    @Test
    void getById() {
        int artistId = 1;
        Artist artist = new Artist();
        artist.setId(artistId);
        artist.setName("Michael A");

        when(artistDao.findById(artistId)).thenReturn(Optional.of(artist));

        Artist result = artistService.getById(artistId);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(artist.getName(), result.getName())
        );

        verify(artistDao).findById(artistId);
    }


    @Test
    void getByIdEmpty() {
        int artistId = 1;

        when(artistDao.findById(artistId)).thenReturn(Optional.empty());

        assertThrows(WrongRequestException.class, () -> artistService.getById(artistId));

        verify(artistDao).findById(artistId);
    }

    @Test
    void update() {
        int artistId = 1;
        Artist artist = new Artist();
        artist.setId(artistId);
        artist.setName("Michael B");

        when(artistDao.save(any(Artist.class))).thenReturn(artist);

        artistService.update(artist);

        verify(artistDao).save(artist);
    }

    @Test
    void delete() {
        int artistId = 1;

        Artist artist = new Artist();
        artist.setId(artistId);
        Optional<Artist> optionalArtist = Optional.of(artist);

        when(artistDao.findById(artistId)).thenReturn(optionalArtist);
        when(trackDao.getAllTracksByArtistId(artistId)).thenReturn(new ArrayList<>());

        doNothing().when(artistDao).deleteById(artistId);

        artistService.delete(artistId);
        verify(artistDao).deleteById(artistId);
    }

    @Test
    void delete_noFound() {
        int artistId = 1;
        String expectedMessage = "There's not an artist with that id.";
        int expectedStatusCode = HttpStatus.NOT_FOUND.value();

        when(artistDao.findById(artistId)).thenReturn(Optional.empty());

        WrongRequestException expectedException = assertThrows(WrongRequestException.class, () -> artistService.delete(artistId));

        assertAll(
                () -> assertEquals(expectedMessage, expectedException.getMessage()),
                () -> assertEquals(expectedStatusCode, expectedException.getHttpStatus().value())
        );

        verify(artistDao ).findById(artistId);
        verify(trackDao, never()).getAllTracksByArtistId(artistId);
        verify(artistDao, never()).deleteById(artistId);
    }

    @Test
    void getAll() {
        List<Artist> artists = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Artist artist = new Artist();
            artist.setId(i + 1);
            artist.setName("Michael " + (i + 1));
            artists.add(artist);
        }

        when(artistDao.findAll()).thenReturn(artists);

        List<Artist> result = artistService.getAll();

        assertAll(
                () -> assertEquals(artists.size(), result.size()),
                () -> assertEquals(artists.getFirst().getName(), result.getFirst().getName()),
                () -> assertEquals(artists.getLast().getName(), result.getLast().getName())
        );

        verify(artistDao).findAll();
    }

    @Test
    void getArtistsByIds() {
        List<Artist> artists = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            Artist artist = new Artist();
            artist.setId(i * 2);
            artist.setName("Michael " + (i + 1));
            artists.add(artist);
        }

        when(artistDao.getByIdIn(anyList())).thenReturn(artists);

        List<Artist> result = artistService.getArtistsByIds(artists.stream().map(Artist::getId).toList());

        assertAll(
                () -> assertEquals(artists.size(), result.size()),
                () -> assertEquals(artists.getFirst().getName(), result.getFirst().getName()),
                () -> assertEquals(artists.getLast().getName(), result.getLast().getName())
        );

        verify(artistDao).getByIdIn(anyList());
    }

    @Test
    void getArtistByName() {
        String artistName = "Michael";

        List<Artist> artists = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            Artist artist = new Artist();
            artist.setId(i);
            artist.setName("Michael " + (i + 1));
            artists.add(artist);
        }

        when(artistDao.getByNameContaining(artistName)).thenReturn(artists);

        List<Artist> result = artistService.getArtistByName(artistName);

        assertAll(
                () -> assertEquals(artists.size(), result.size()),
                () -> assertEquals(artists.getFirst().getName(), result.getFirst().getName()),
                () -> assertEquals(artists.getLast().getName(), result.getLast().getName())
        );

        verify(artistDao).getByNameContaining(artistName);
    }

    @Test
    void getTracksByArtist() {
        int artistId = 1;
        Artist artist = new Artist();
        artist.setId(artistId);
        artist.setName("Michael J");

        for (int i = 0; i < 5; i++) {
            String title = "Dance Dance Dance " + (i + 1) + "!!!!";
            Track newTrack = new Track();
            newTrack.setId(i + 1);
            newTrack.setTitle(title);
            newTrack.getArtists().add(artist);
            artist.getTracks().add(newTrack);
        }

        when(artistDao.findById(artistId)).thenReturn(Optional.of(artist));

        List<Track> result = artistService.getTracksByArtist(artistId);

        assertAll(
                () -> assertEquals(artist.getTracks().size(), result.size()),
                () -> assertEquals(artist.getTracks().stream().toList().getFirst().getTitle(), result.getFirst().getTitle()),
                () -> assertEquals(artist.getTracks().stream().toList().getLast().getTitle(), result.getLast().getTitle())
        );

        verify(artistDao).findById(artistId);
    }

    @Test
    void getTracksByArtist_noFound() {
        int artistId = 1;
        String expectedMessage = "There's not an artist with that id.";
        int expectedStatusCode = HttpStatus.NOT_FOUND.value();

        when(artistDao.findById(artistId)).thenReturn(Optional.empty());

        WrongRequestException exception = assertThrows(WrongRequestException.class, () -> artistService.getTracksByArtist(artistId));

        assertAll(
                () -> assertEquals(expectedMessage, exception.getMessage()),
                () -> assertEquals(expectedStatusCode, exception.getHttpStatus().value())
        );

        verify(artistDao).findById(artistId);
    }
}