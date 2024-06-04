package com.expeditors.tracksartists.dataAccessObjects;

import com.expeditors.tracksartists.services.implemetations.models.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITrackDao extends JpaRepository<Track, Integer> {
//    public List<Track> getByMediaType(MediaType eMediaType);
//    public List<Track> getByYearOfIssueDate(int year);
//    public List<Track> getAllTracksByArtistId(int artistId);
}
