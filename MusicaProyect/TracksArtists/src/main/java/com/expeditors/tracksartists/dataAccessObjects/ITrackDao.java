package com.expeditors.tracksartists.dataAccessObjects;

import com.expeditors.tracksartists.enums.MediaType;
import com.expeditors.tracksartists.models.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITrackDao extends JpaRepository<Track, Integer> {
//    public List<Track> getByMediaType(MediaType eMediaType);
//    public List<Track> getByYearOfIssueDate(int year);
//    public List<Track> getAllTracksByArtistId(int artistId);
}
