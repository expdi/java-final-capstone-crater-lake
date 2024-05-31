package com.expeditors.tracksartists.models;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Artist {
    private int id;

    @NotNull
    private String name;

    public Artist(){

    }
    public Artist(int id, String name) {
        this.name = name;
        this.id = id;
    }
}
