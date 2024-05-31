package com.expeditors.tracksartists.controllers;

import com.expeditors.tracksartists.models.Artist;
import com.expeditors.tracksartists.models.Track;
import com.expeditors.tracksartists.services.implemetations.ArtistServiceImpl;
import com.expeditors.tracksartists.services.interfaces.IArtistService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artist/")
public class ArtistController {

    @Autowired public IArtistService artistService;

    @GetMapping("getAll")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.status(HttpStatus.FOUND).body(this.artistService.getAll());
    }

    @GetMapping("get/{id}")
    public ResponseEntity<?> getById(@PathVariable int id){
        Artist artist = this.artistService.getById(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(artist);
    }

    @GetMapping("getTracksByArtist/{id}")
    public ResponseEntity<?> getTracksByArtist(@PathVariable int id){
        List<Track> tracks = this.artistService.getTracksByArtist(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(tracks);
    }

    @GetMapping("getByName/{name}")
    public ResponseEntity<?> getArtistByName(@PathVariable String name){
        List<Artist> artists = this.artistService.getArtistByName(name);
        return ResponseEntity.status(HttpStatus.FOUND).body(artists);
    }

    @PostMapping("add")
    public ResponseEntity<?> addArtist(@RequestBody @Valid Artist artist){
        Artist artistCreated = this.artistService.add(artist);
        return ResponseEntity.status(HttpStatus.CREATED).body(artistCreated);
    }

    @PutMapping("updateArtist")
    public ResponseEntity<?> updateArtist(@RequestBody @Valid Artist artist){
        this.artistService.update(artist);
        return ResponseEntity.ok("Success");
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteArtist(@PathVariable int id){
        this.artistService.delete(id);
        return ResponseEntity.ok("Success");
    }
}
