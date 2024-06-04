package com.expeditors.tracksartists.services.interfaces;

import com.expeditors.tracksartists.models.Artist;
import com.expeditors.tracksartists.models.Track;

import java.util.List;

public interface IArtistService {

    Artist add(Artist artist);
    Artist getById(int id);
    void  update(Artist artist);
    void delete(int id);
    List<Artist> getAll();
    List<Artist> getArtistsByIds(List<Integer> artistsIds);
    List<Artist> getArtistByName(String name);
    List<Track> getTracksByArtist(int idArtist);
}
