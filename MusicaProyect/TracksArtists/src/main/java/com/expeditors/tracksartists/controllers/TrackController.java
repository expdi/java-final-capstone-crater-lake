package com.expeditors.tracksartists.controllers;

import com.expeditors.tracksartists.enums.DEvaluation;
import com.expeditors.tracksartists.enums.MediaType;
import com.expeditors.tracksartists.exceptionHandlers.exceptions.WrongRequestException;
import com.expeditors.tracksartists.models.Artist;
import com.expeditors.tracksartists.models.Track;
import com.expeditors.tracksartists.services.interfaces.IArtistService;
import com.expeditors.tracksartists.services.interfaces.ITrackService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@RequestMapping("/api/track/")
public class TrackController {
    public final ITrackService trackService;
    public final IArtistService artistService;
    private final RestClient restClient;

    public TrackController(IArtistService artistService, ITrackService trackService) {
        var baseUrl = "http://localhost:10001/api/pricing/";

        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Accept", "application/json")
                .defaultHeader("Content-Type", "application/json")
                .build();

        this.artistService = artistService;
        this.trackService = trackService;
    }

    @GetMapping("get/{id}")
    public ResponseEntity<?> getTrackById(@PathVariable int id){
        Track track = this.trackService.getById(id);
        track.setPrice(1.1);
        return ResponseEntity.ok(track);
    }

    @GetMapping("getAll")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(this.trackService.getAll());
    }

    @GetMapping("getTracksBySpecificMediaType/{mediaType}")
    public ResponseEntity<?> getTracksBySpecificMediaType(@PathVariable MediaType mediaType){
        List<Track> tracks = this.trackService.getAllByMediaType(mediaType);
        tracks.forEach(track -> track.setPrice(1.1));
        return ResponseEntity.ok(tracks);
    }

    @GetMapping("getTracksBySpecificYearOfIssueDate/{year}")
    public ResponseEntity<?> getTracksBySpecificYearOfIssueDate(@PathVariable int year){
        List<Track> tracks = this.trackService.getAllByIssueDateYear(year);
        tracks.forEach(track -> track.setPrice(1.1));
        return ResponseEntity.ok(tracks);
    }

    @GetMapping("getArtistsByTrack/{id}")
    public ResponseEntity<?> getArtistsByTrack(@PathVariable int id){
        List<Artist> artists = this.trackService.getArtistByTrack(id);
        return ResponseEntity.ok(artists);
    }

    @GetMapping("getByDurationDynamic")
    public ResponseEntity<?> getByDurationDynamic(@RequestParam("duration") Integer seconds, @RequestParam("devaluation") DEvaluation dEvaluation){
        List<Track> tracks = this.trackService.getByDurationDynamic(seconds, dEvaluation);
        tracks.forEach(track -> track.setPrice(1.1));
        return ResponseEntity.status(HttpStatus.FOUND).body(tracks);
    }

    @PostMapping("add")
    public ResponseEntity<Track> addTrack(@RequestBody @Valid Track track){
        Track trackAdded =  this.trackService.add(track);
        track.setPrice(this.getPrice());
        return ResponseEntity.status(HttpStatus.CREATED).body(trackAdded);
    }

    @PutMapping("update")
    public ResponseEntity<?> updateArtist(@RequestBody @Valid Track track){
        this.trackService.update(track);
        return ResponseEntity.ok("Success");
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteArtist(@PathVariable int id){
        this.trackService.delete(id);
        return ResponseEntity.ok("Success");
    }

    private double getPrice(){
        ResponseEntity<Double> response = restClient.get()
                .retrieve()
                .toEntity(Double.class);

        if(response.getBody() == null){
            throw new WrongRequestException("Pricing Service return null value", HttpStatus.SERVICE_UNAVAILABLE, response);
        }

        return response.getBody();
    }
}
