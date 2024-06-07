package com.expeditors.tracksartists.controllers;

import com.expeditors.tracksartists.models.Artist;
import com.expeditors.tracksartists.models.Track;
import com.expeditors.tracksartists.services.implemetations.ArtistServiceImpl;
import com.expeditors.tracksartists.services.implemetations.TrackServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ArtistController.class)
//@WithMockUser(username = "alanaudo", roles = {"USER"})
class ArtistControllerTest {
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
                //.apply(springSecurity())
                .build();
    }

    @Test
    void getAll() throws Exception {
        List<Artist> artists = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Artist artist = new Artist();
            artist.setId(i + 1);
            artist.setName("Jay #" + (i + 1));
            artists.add(artist);
        }

        when(artistService.getAll()).thenReturn(artists);

        mockMvc.perform(
                        get("/api/artist/getAll")
                                .with(httpBasic("alanaudo", "password"))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isFound())
                .andDo(print());

        verify(artistService).getAll();
    }

    @Test
    void getById() throws Exception {
        int artistId = 1;

        Artist artist = new Artist();
        artist.setId(1);
        artist.setName("Michael J");

        when(artistService.getById(artistId)).thenReturn(artist);

        mockMvc.perform(
                        get("/api/artist/get/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isFound())
                .andDo(print());

        verify(artistService).getById(artistId);
    }

    @Test
    void getTracksByArtist() throws Exception {
        Artist artist = new Artist();
        artist.setId(1);
        artist.setName("Michael J");

        for (int i = 0; i < 5; i++) {
            String title = "Dance Dance Dance " + (i + 1) + "!!!!";
            Track newTrack = new Track();
            newTrack.setId(i + 1);
            newTrack.setTitle(title);

            artist.getTracks().add(newTrack);
        }

        when(artistService.getTracksByArtist(artist.getId())).thenReturn(new ArrayList<>(artist.getTracks()));

        mockMvc.perform(
                        get("/api/artist/getTracksByArtist/{id}", artist.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isFound())
                .andDo(print());

        verify(artistService).getTracksByArtist(artist.getId());
    }

    @Test
    void getArtistByName() throws Exception {
        List<Artist> artists = new ArrayList<>();
        String artistName = "Jay";

        for (int i = 0; i < 5; i++) {
            Artist artist = new Artist();
            artist.setId(i + 1);
            artist.setName(artistName + " #" + (i + 1));
            artists.add(artist);
        }

        when(artistService.getArtistByName(artistName)).thenReturn(artists);

        mockMvc.perform(
                        get("/api/artist/getByName/{name}", artistName)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isFound())
                .andDo(print());

        verify(artistService).getArtistByName(artistName);
    }

    @Test
    void getArtistsByIds() throws Exception {
        List<Artist> artists = new ArrayList<>();
        String artistName = "Jay";

        for (int i = 0; i < 2; i++) {
            Artist artist = new Artist();
            artist.setId(i + 1);
            artist.setName(artistName + " #" + (i + 1));
            artists.add(artist);
        }

        List<Integer> artistIds = artists.stream().map(Artist::getId).toList();

        when(artistService.getArtistsByIds(anyList())).thenReturn(artists);

        mockMvc.perform(
                        get("/api/artist/getArtistsByIds/{ids}", "1,2")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isFound())
                .andDo(print());

        verify(artistService).getArtistsByIds(artistIds);
    }

    @Test
    void addArtist() throws Exception {
        Artist artist = new Artist();
        artist.setId(400);
        artist.setName("Michael A");

        String artistJson = mapper.writeValueAsString(artist);

        when(artistService.add(any(Artist.class))).thenReturn(artist);

        mockMvc.perform(
                        post("/api/artist/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(artistJson)
                )
                .andExpect(status().isCreated())
                .andDo(print());

        verify(artistService).add(any(Artist.class));
    }

    @Test
    void updateArtist() throws Exception {
        Artist artist = new Artist();
        artist.setId(405);
        artist.setName("Michael B");

        String artistJson = mapper.writeValueAsString(artist);

        doNothing().when(artistService).update(any(Artist.class));

        mockMvc.perform(
                        put("/api/artist/updateArtist")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(artistJson)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Success")));

        verify(artistService).update(any(Artist.class));
    }

    @Test
    void deleteArtist() throws Exception {
        int artistId = 600;

        doNothing().when(artistService).delete(artistId);

        mockMvc.perform(
                        delete("/api/artist/delete/{id}", artistId)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());

        verify(artistService).delete(artistId);
    }
}
