package com.expeditors.tracksartists.dataAccessObjects.Interfaces;

import com.expeditors.tracksartists.enums.MediaType;
import com.expeditors.tracksartists.models.Track;

import java.util.List;

public interface ITrackDao extends IBasicRepository<Track>{
    public List<Track> getByMediaType(MediaType eMediaType);
    public List<Track> getByYearOfIssueDate(int year);
    public List<Track> getAllTracksByArtistId(int artistId);
    public List<Track> getAll();
}
