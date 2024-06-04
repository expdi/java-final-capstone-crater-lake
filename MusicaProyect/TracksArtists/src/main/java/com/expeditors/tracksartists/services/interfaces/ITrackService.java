package com.expeditors.tracksartists.services.interfaces;

import com.expeditors.tracksartists.enums.DEvaluation;
import com.expeditors.tracksartists.enums.MediaType;
import com.expeditors.tracksartists.services.implemetations.models.Artist;
import com.expeditors.tracksartists.services.implemetations.models.Track;

import java.util.List;

public interface ITrackService {
    Track add(Track track);
    Track getById(int id);
    void update(Track track);
    void delete(int id);
    List<Track> getAllByMediaType(MediaType mediaType);
    List<Track> getAllByIssueDateYear(int year);
    List<Artist> getArtistByTrack(int idTrack);
    List<Track> getByDurationDynamic(Integer seconds, DEvaluation dEvaluation);
    List<Track> getAll();
}
