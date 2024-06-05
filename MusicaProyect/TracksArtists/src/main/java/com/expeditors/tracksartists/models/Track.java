package com.expeditors.tracksartists.models;


import com.expeditors.tracksartists.enums.MediaType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @NotEmpty
    private String title;

    @NotNull
    @NotEmpty
    private String album;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinTable(name = "artist_track",
            joinColumns = @JoinColumn(name = "track_id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id"))
    private Set<Artist> artists = new HashSet<>();

    private LocalDateTime issueDate;
    private Duration duration;

    @Enumerated(EnumType.ORDINAL)
    private MediaType mediaType;

    private Double price;
}