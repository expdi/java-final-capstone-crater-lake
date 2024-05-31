package com.expeditors.tracksartists.Services;

import com.expeditors.tracksartists.dataAccessObjects.implemetations.ArtistDaoImpl;
import com.expeditors.tracksartists.dataAccessObjects.implemetations.TrackDaoImpl;
import com.expeditors.tracksartists.models.Track;
import com.expeditors.tracksartists.services.implemetations.TrackServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class TracksServicesTests {

    @Mock
    private ArtistDaoImpl artistDao;
    @InjectMocks
    private TrackServiceImpl trackService;
    @Mock
    private TrackDaoImpl trackDao;
    List<Integer> artists = List.of(1);

    @Test
    public void testCreateTrack() {
        Mockito.when(artistDao.validateAllArtistsExists(artists)).thenReturn(true);
        Track track = Track.builder().title("title").album("album").artists(artists).build();
        Mockito.when(trackDao.add(track)).thenReturn(track);
        Track track1 = trackService.add(track);
        assertNotNull(track1);
    }

    @Test
    public void testGetTrackById() {
        Track track = Track.builder().id(1).title("title").album("album").artists(artists).build();
        Mockito.when(trackDao.getById(1)).thenReturn(track);
        Track track1 = trackService.getById(1);
        assertNotNull(track1);
    }

}
