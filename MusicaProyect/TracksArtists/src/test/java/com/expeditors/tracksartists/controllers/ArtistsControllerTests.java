package com.expeditors.tracksartists.controllers;

import com.expeditors.tracksartists.models.Artist;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class ArtistsControllerTests {
    @Autowired private MockMvc mockArtistsController;

    @Test
    void getAllTest() throws Exception {
        this.mockArtistsController.perform(MockMvcRequestBuilders.get("/api/artist/getAll"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isFound());
    }

    @Test
    void getByIdTest() throws Exception {
        this.mockArtistsController.perform(MockMvcRequestBuilders.get("/api/artist/get/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().is(302))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void getTracksByArtistTest() throws Exception {
        this.mockArtistsController.perform(MockMvcRequestBuilders.get("/api/artist/getTracksByArtist/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void getByNameTest() throws Exception {

        this.mockArtistsController.perform(MockMvcRequestBuilders.get("/api/artist/getByName/{name}", "Lana Del Rey"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void addTest() throws Exception {
        String json = new ObjectMapper().writeValueAsString(new Artist(1, "Lana Del Rey"));

        this.mockArtistsController.perform(MockMvcRequestBuilders.post("/api/artist/add")
                                                                .accept(MediaType.APPLICATION_JSON)
                                                                .contentType(MediaType.APPLICATION_JSON)
                                                                .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void updateTest() throws Exception {
        String json = new ObjectMapper().writeValueAsString(new Artist(1, "Lana Del Rey"));

        this.mockArtistsController.perform(MockMvcRequestBuilders.put("/api/artist/update")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void deleteTest() throws Exception {
        this.mockArtistsController.perform(MockMvcRequestBuilders.delete("/api/artist/delete/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andDo(MockMvcResultHandlers.print());
    }
}
