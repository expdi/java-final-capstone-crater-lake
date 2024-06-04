package com.expeditors.tracksartists.models;


import com.expeditors.tracksartists.enums.MediaType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

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

    @NotNull(message = "Artist list is empty")
    @Size(message = "Every track must contain at least one Artist related", min = 1)
    @ManyToMany(mappedBy = "tracks")
    private Set<Artist> artists = new HashSet<>();

    private LocalDateTime issueDate;
    private Duration duration;

    @Enumerated(EnumType.ORDINAL)
    private MediaType mediaType;

    private Double price;
}