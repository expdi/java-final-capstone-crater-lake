package com.expeditors.tracksartists.dataAccessObjects;

import com.expeditors.tracksartists.models.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IArtistDao extends JpaRepository<Artist, Integer> {
    public List<Artist> getByName(String name);
//    public List<Artist> getArtistsByIdList(List<Integer> artistsIds);
}
