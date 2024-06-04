package com.expeditors.tracksartists.services.implemetations;

import com.expeditors.tracksartists.dataAccessObjects.IArtistDao;
import com.expeditors.tracksartists.dataAccessObjects.ITrackDao;
import com.expeditors.tracksartists.enums.DEvaluation;
import com.expeditors.tracksartists.enums.MediaType;
import com.expeditors.tracksartists.exceptionHandlers.exceptions.InvalidBusinessLogicFieldException;
import com.expeditors.tracksartists.exceptionHandlers.exceptions.WrongRequestException;
import com.expeditors.tracksartists.models.Artist;
import com.expeditors.tracksartists.models.Track;
import com.expeditors.tracksartists.services.interfaces.ITrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TrackServiceImpl implements ITrackService {

    private final ITrackDao trackDao;
    private final IArtistDao artistDao;

    public TrackServiceImpl(ITrackDao trackDao, IArtistDao artistDao) {

        this.trackDao = trackDao;
        this.artistDao = artistDao;
    }

    @Override
    public Track add(Track track){
        List<Integer> ids = track.getArtists().stream().map(artist -> artist.getId()).toList();
        track.getArtists().clear();

        ids.forEach(id -> {
            Optional<Artist> artist = this.artistDao.findById(id);
            track.getArtists().add(artist.get());
        });

        return trackDao.save(track);
    }

    @Override
    public Track getById(int id){
        Optional<Track> track = this.trackDao.findById(id);

        if(track.isEmpty()){
            throw new WrongRequestException("Track not found with the specific id", HttpStatus.NOT_FOUND, id);
        }

        return track.get();
    }

    @Override
    public void update(Track track) {
        this.trackDao.save(track);
    }

    @Override
    public void delete(int id) {
        this.trackDao.deleteById(id);
    }

    @Override
    public List<Track> getAll() {
        return this.trackDao.findAll();
    }

    @Override
    public List<Track> getAllByMediaType(MediaType mediaType) {
        return this.trackDao.findAllByMediaType(mediaType);
    }


    @Override
    public List<Track> getAllByIssueDateYear(int year) {

//        return this.trackDao.getByYearOfIssueDate(year);
        return null;
    }

    @Override
    public List<Artist> getArtistByTrack(int idTrack) {
//        List<Integer> artistsIds = this.getById(idTrack).getArtists();
//        return this.artistDao.getArtistsByIdList(artistsIds);

        return  null;
    }

    @Override
    public List<Track> getByDurationDynamic(Integer seconds, DEvaluation dEvaluation) {
//        List<Track> tracks = this.trackDao.getAll();
//
//        return switch (dEvaluation){
//            case DEvaluation.SHORTER -> tracks.stream().filter(track -> track.getDuration().toSeconds() < seconds).toList();
//            case DEvaluation.EQUAL -> tracks.stream().filter(track -> track.getDuration().toSeconds() == seconds).toList();
//            case DEvaluation.LONGER -> tracks.stream().filter(track -> track.getDuration().toSeconds() > seconds).toList();
//        };

        return  null;
    }
}
