package com.expeditors.tracksartists.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Getter(onMethod = @__( @JsonIgnore))
    @ManyToMany(mappedBy = "artists")
    private Set<Track> tracks = new HashSet<>();
}