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

    @JsonIgnore
    @ManyToMany(mappedBy = "artists", cascade = CascadeType.DETACH)
    private Set<Track> tracks = new HashSet<>();
}