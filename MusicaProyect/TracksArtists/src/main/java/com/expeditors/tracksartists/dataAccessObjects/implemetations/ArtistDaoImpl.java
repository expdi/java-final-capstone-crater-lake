package com.expeditors.tracksartists.dataAccessObjects.implemetations;

import com.expeditors.tracksartists.dataAccessObjects.Interfaces.IArtistDao;
import com.expeditors.tracksartists.dataAccessObjects.Interfaces.IBasicRepository;
import com.expeditors.tracksartists.models.Artist;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@Profile("profile1")
public class ArtistDaoImpl implements IArtistDao {

    private Map<Integer, Artist> artists = new HashMap<>();
    private int idsCounter = 1;

    @Override
    public Artist add(Artist artist) {
        artist.setId(idsCounter++);
        this.artists.put(artist.getId(), artist);
        return artist;
    }

    @Override
    public boolean update(Artist artist) {
        return this.artists.put(artist.getId(), artist) != null;
    }

    @Override
    public boolean delete(int id) {
        return this.artists.remove(id) != null;
    }

    @Override
    public Artist getById(int id) {
        return this.artists.get(id);
    }

    @Override
    public List<Artist> getByName(String name) {
        return this.artists.values().stream().filter(artist -> artist.getName().equals(name)).toList();
    }

    @Override
    public List<Artist> getAll() {
        return new ArrayList<>(this.artists.values());
    }

    @Override
    public List<Artist> getArtistsByIdList(List<Integer> artistsIds){
        return this.artists.values().stream().filter(artist -> artistsIds.stream().anyMatch(id -> id == artist.getId()))
                .collect(Collectors.toList());
    }

    public boolean validateAllArtistsExists(List<Integer> artistsIds){
        if(this.artists.isEmpty()){
            return false;
        }

        for (int artistId: artistsIds) {
            if(this.artists.get(artistId) == null){
                return false;
            }
        }

        return true;
    }
}
