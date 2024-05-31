package com.expeditors.tracksartists.dataAccessObjects.implemetations;

import com.expeditors.tracksartists.dataAccessObjects.Interfaces.IBasicRepository;
import com.expeditors.tracksartists.dataAccessObjects.Interfaces.ITrackDao;
import com.expeditors.tracksartists.enums.MediaType;
import com.expeditors.tracksartists.models.Track;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Profile("profile1")
public class TrackDaoImpl implements ITrackDao {
    private Map<Integer, Track> tracks = new HashMap<>();
    private int idsCounter = 1;

    @Override
    public Track add(Track track) {
        track.setId(idsCounter++);
        this.tracks.put(track.getId(), track);
        return track;
    }

    @Override
    public boolean update(Track track) {
        return this.tracks.put(track.getId(), track) == null;
    }

    @Override
    public boolean delete(int id) {
        return this.tracks.remove(id) != null;
    }

    @Override
    public Track getById(int id) {
        return this.tracks.get(id);
    }

    @Override
    public List<Track> getByMediaType(MediaType eMediaType) {
        return this.tracks.values().stream().filter(track -> track.getEMediaType() == eMediaType).toList();
    }

    @Override
    public List<Track> getByYearOfIssueDate(int year) {
        return this.tracks.values().stream().filter(track -> track.getIssueDate().getYear() == year).toList();
    }

    @Override
    public List<Track> getAll(){
        return new ArrayList<>(this.tracks.values());
    }


    @Override
    public List<Track> getAllTracksByArtistId(int artistId) {
        return this.tracks.values().stream().filter(track -> track.getArtists().stream().anyMatch(idArtist -> idArtist == artistId)).toList();
    }
}
