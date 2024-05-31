package com.expeditors.tracksartists.models;


import com.expeditors.tracksartists.enums.MediaType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
public class Track {
    private int id;

    @NotNull
    @NotEmpty
    private String title;

    @NotNull
    @NotEmpty
    private String album;

    @NotNull(message = "Artist list is empty")
    @Size(message = "Every track must contain at least one Artist related", min = 1)
    @Setter(AccessLevel.NONE)
    private List<Integer> artists;

    @Setter(AccessLevel.NONE)
    private List<Artist> artistsInfo;

    private LocalDateTime issueDate;
    private Duration duration;
    private MediaType eMediaType;
    private Double price;

    public void addArtist(int idArtist){
        this.artists.add(idArtist);
    }

    public boolean removeArtist(Integer idArtist){
        return this.artists.remove(idArtist);
    }

    public void addArtists(List<Integer> artists){
        this.artists.addAll(artists);
    }

    public boolean removeArtists(List<Integer> artists){
        return this.artists.removeAll(artists);
    }

    public List<Integer> getArtists(){
        if(this.artists == null)
            return new ArrayList<>();

        return new ArrayList<>(this.artists);
    }

    public void setArtistsInfo(List<Artist> artistsInfo){
        this.artistsInfo = artistsInfo.stream()
                .filter(artist -> this.artists.stream().anyMatch(idArtirst -> idArtirst == artist.getId()))
                .collect(Collectors.toList());
    }
}
