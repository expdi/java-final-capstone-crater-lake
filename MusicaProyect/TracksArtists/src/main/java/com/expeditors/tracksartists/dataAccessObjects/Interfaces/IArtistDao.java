package com.expeditors.tracksartists.dataAccessObjects.Interfaces;

import com.expeditors.tracksartists.models.Artist;

import java.util.List;

public interface IArtistDao extends IBasicRepository<Artist> {
    public List<Artist> getByName(String name);
    public List<Artist> getAll();
    public List<Artist> getArtistsByIdList(List<Integer> artistsIds);
}
