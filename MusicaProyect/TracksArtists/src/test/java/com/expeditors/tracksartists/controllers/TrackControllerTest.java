package com.expeditors.tracksartists.controllers;

import com.expeditors.tracksartists.enums.DEvaluation;
import com.expeditors.tracksartists.enums.MediaType;
import com.expeditors.tracksartists.models.Artist;
import com.expeditors.tracksartists.models.Track;
import com.expeditors.tracksartists.services.implemetations.ArtistServiceImpl;
import com.expeditors.tracksartists.services.implemetations.TrackServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Mockito.*;
//import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TrackController.class)
//@WithMockUser(username = "alanaudo", roles = {"USER"})
class TrackControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrackServiceImpl trackService;

    @MockBean
    private ArtistServiceImpl artistService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
//                .apply(springSecurity())
                .build();
    }

    @Test
    void getTrackById() throws Exception {
        int trackId = 540;

        Track track = Mockito.mock(Track.class);
        track.setId(trackId);
        track.setTitle("Track #540");
        track.setMediaType(MediaType.MP3);
        track.setDuration(Duration.ofSeconds(150));
        track.setAlbum("Album #1");

        when(track.getPrice()).thenReturn(1.0);

        when(trackService.getById(trackId)).thenReturn(track);

        mockMvc.perform(get("/api/track/get/{id}", trackId)
                                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
//                        .with(httpBasic("alanaudo", "password"))
                )
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(track)))
                .andDo(print());

        verify(trackService).getById(trackId);
    }

    @Test
    void getAll() throws Exception {
        List<Track> tracks = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            String title = "Dance Dance Dance " + (i + 1) + "!!!!";
            Track newTrack = new Track();
            newTrack.setId(i + 1);
            newTrack.setTitle(title);

            tracks.add(newTrack);
        }

        when(trackService.getAll()).thenReturn(tracks);

        mockMvc.perform(
                get("/api/track/getAll")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getTracksBySpecificMediaType() throws Exception {
        MediaType mediaType = MediaType.MP3;

        List<Track> mockTracks = List.of(
                new Track(),
                new Track()
        );

        when(trackService.getAllByMediaType(mediaType)).thenReturn(mockTracks);

        mockMvc.perform(
                        get("/api/track/getTracksBySpecificMediaType/{mediaType}", mediaType)
                                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());

        verify(trackService).getAllByMediaType(mediaType);
    }

    @Test
    void getTracksBySpecificYearOfIssueDate() throws Exception {
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

        when(trackService.getAllByIssueDateYear(issueDate.getYear())).thenReturn(tracks);

        mockMvc.perform(
                        get("/api/track/getTracksBySpecificYearOfIssueDate/{year}", issueDate.getYear())
                                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());

        verify(trackService).getAllByIssueDateYear(issueDate.getYear());
    }

    @Test
    void getArtistsByTrack() throws Exception {
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

        when(trackService.getArtistByTrack(track.getId())).thenReturn(track.getArtists().stream().toList());

        mockMvc.perform(
                        get("/api/track/getArtistsByTrack/{id}", track.getId())
                                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());

        verify(trackService).getArtistByTrack(track.getId());
    }

    @Test
    void getByDurationDynamic() throws Exception {
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

        when(trackService.getByDurationDynamic(seconds, evaluation)).thenReturn(tracks);

        mockMvc.perform(
                        get("/api/track/getByDurationDynamic")
                                .param("duration", String.valueOf(seconds))
                                .param("devaluation", evaluation.toString())
                                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isFound())
                .andDo(print());

        verify(trackService).getByDurationDynamic(seconds, evaluation);
    }

    @Test
    void addTrack() throws Exception {
        Track track = mock(Track.class);

        when(track.getId()).thenReturn(1);
        when(track.getTitle()).thenReturn("Track #1");
        when(track.getAlbum()).thenReturn("Album #1");
        when(track.getPrice()).thenReturn(1.25);

        doNothing().when(track).setPrice(1.25);

        String trackJson = mapper.writeValueAsString(track);

        when(trackService.add(any(Track.class))).thenReturn(track);

        mockMvc.perform(
                        post("/api/track/add")
                                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                                .content(trackJson)
                )
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();

        verify(trackService).add(any(Track.class));
    }

    @Test
    void updateArtist() throws Exception {
        Track track = new Track();
        track.setId(1);
        track.setTitle("Track #1");
        track.setAlbum("Album #1");
        track.setPrice(1.25);

        String trackJson = mapper.writeValueAsString(track);

        doNothing().when(trackService).update(any(Track.class));

        mockMvc.perform(
                        put("/api/track/update")
                                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                                .content(trackJson)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Success" )));

        verify(trackService).update(any(Track.class));
    }

    @Test
    void deleteArtist() throws Exception {
        int trackId = 125;

        doNothing().when(trackService).delete(trackId);

        mockMvc.perform(
                        delete("/api/track/delete/{id}", trackId)
                                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());

        verify(trackService).delete(trackId);
    }
}