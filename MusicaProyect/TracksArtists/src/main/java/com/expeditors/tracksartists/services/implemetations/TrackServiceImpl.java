package com.expeditors.tracksartists.services.implemetations;

import com.expeditors.tracksartists.dataAccessObjects.implemetations.ArtistDaoImpl;
import com.expeditors.tracksartists.dataAccessObjects.implemetations.TrackDaoImpl;
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

@Service
@Profile("profile1")
public class TrackServiceImpl implements ITrackService {

    @Autowired private TrackDaoImpl trackDao;
    @Autowired private ArtistDaoImpl artistDao;

    @Override
    public Track add(Track track){
        this.executePrevalidations(track);
        return trackDao.add(track);
    }

    @Override
    public Track getById(int id){
        Track track = this.trackDao.getById(id);

        if(track == null){
            throw new WrongRequestException("Track not found with the specific id", HttpStatus.NOT_FOUND, id);
        }

        return track;
    }

    @Override
    public void update(Track track) {
        this.executePrevalidations(track);

        boolean updateWasSuccessful = this.trackDao.update(track);
        if(!updateWasSuccessful){
            throw new WrongRequestException("The update was not process, please check your entity", HttpStatus.BAD_REQUEST, track);
        }
    }

    @Override
    public void delete(int id) {
        boolean deleteWasSuccessful = this.trackDao.delete(id);

        if (!deleteWasSuccessful) {
            throw new WrongRequestException("The delete was not process, please check the entity that you want to delete", HttpStatus.BAD_REQUEST, id);
        }
    }

    @Override
    public List<Track> getAll() {
        return new ArrayList<>(this.trackDao.getAll());
    }

    @Override
    public List<Track> getAllByMediaType(MediaType mediaType) {
        return this.trackDao.getByMediaType(mediaType);
    }

    @Override
    public List<Track> getAllByIssueDateYear(int year) {
        return this.trackDao.getByYearOfIssueDate(year);
    }

    @Override
    public List<Artist> getArtistByTrack(int idTrack) {
        List<Integer> artistsIds = this.getById(idTrack).getArtists();
        return this.artistDao.getArtistsByIdList(artistsIds);
    }

    @Override
    public List<Track> getByDurationDynamic(Integer seconds, DEvaluation dEvaluation) {
        List<Track> tracks = this.trackDao.getAll();

        return switch (dEvaluation){
            case DEvaluation.SHORTER -> tracks.stream().filter(track -> track.getDuration().toSeconds() < seconds).toList();
            case DEvaluation.EQUAL -> tracks.stream().filter(track -> track.getDuration().toSeconds() == seconds).toList();
            case DEvaluation.LONGER -> tracks.stream().filter(track -> track.getDuration().toSeconds() > seconds).toList();
        };
    }

    private void executePrevalidations(Track track){
        if(!this.artistDao.validateAllArtistsExists(track.getArtists())){
            throw new InvalidBusinessLogicFieldException("Some artists does not exists in the database");
        }
    }
}
