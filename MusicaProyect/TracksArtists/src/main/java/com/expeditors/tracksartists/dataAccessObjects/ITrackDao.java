package com.expeditors.tracksartists.dataAccessObjects;

import com.expeditors.tracksartists.enums.MediaType;
import com.expeditors.tracksartists.models.Artist;
import com.expeditors.tracksartists.models.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.List;

@Repository
public interface ITrackDao extends JpaRepository<Track, Integer> {
    List<Track> findAllByMediaType(MediaType eMediaType);

    @Query("SELECT t FROM Track t WHERE extract(year from t.issueDate) = :year")
    List<Track> findAllByIssueDate(int year);

    @Query("SELECT a.tracks FROM Artist a WHERE a.id = :id")
    List<Track> getAllTracksByArtistId(int id);

    @Query("SELECT t.artists FROM Track t WHERE t.id = :id")
    List<Artist> findArtistsByTrack(int id);

    List<Track> findByDurationLessThan(Duration duration);

    List<Track> findByDurationEquals(Duration duration);

    List<Track> findByDurationGreaterThan(Duration duration);
}
