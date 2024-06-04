package com.expeditors.tracksartists.Services;

import com.expeditors.tracksartists.dataAccessObjects.IArtistDao;
import com.expeditors.tracksartists.models.Artist;
import com.expeditors.tracksartists.models.Track;
import com.expeditors.tracksartists.services.implemetations.ArtistServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ArtistServiceTest {
    @Mock
    private IArtistDao artistDao;

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

        when(artistDao.getReferenceById(artistId)).thenReturn(artist);

        Artist result = artistService.getById(artistId);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(artist.getName(), result.getName())
        );

        verify(artistDao).getReferenceById(artistId);
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

        doNothing().when(artistDao).deleteById(artistId);

        artistService.delete(artistId);

        verify(artistDao).deleteById(artistId);
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
    }

    @Test
    void getArtistByName() {
    }

    @Test
    void getTracksByArtist() {
    }
}