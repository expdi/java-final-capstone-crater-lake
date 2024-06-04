package com.expeditors.tracksartists.dataAccessObjects;

import com.expeditors.tracksartists.services.implemetations.models.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IArtistDao extends JpaRepository<Artist, Integer> {
    List<Artist> getByIdIn(List<Integer> ids);

    List<Artist> getByNameContaining(String name);
}
