package com.expeditors.tracksartists.services.implemetations;

import com.expeditors.tracksartists.dataAccessObjects.ITrackDao;
import com.expeditors.tracksartists.enums.DEvaluation;
import com.expeditors.tracksartists.enums.MediaType;
import com.expeditors.tracksartists.services.implemetations.models.Artist;
import com.expeditors.tracksartists.services.implemetations.models.Track;
import com.expeditors.tracksartists.services.interfaces.ITrackService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrackServiceImpl implements ITrackService {

    private final ITrackDao trackDao;

    public TrackServiceImpl(ITrackDao trackDao) {
        this.trackDao = trackDao;
    }

    @Override
    public Track add(Track track){
        this.executePrevalidations(track);
        return trackDao.save(track);
    }

    @Override
    public Track getById(int id){
        Track track = this.trackDao.getReferenceById(id);

//        if(track == null){
//            throw new WrongRequestException("Track not found with the specific id", HttpStatus.NOT_FOUND, id);
//        }

        return track;
    }

    @Override
    public void update(Track track) {
        this.executePrevalidations(track);

        this.trackDao.save(track);
//        if(!updateWasSuccessful){
//            throw new WrongRequestException("The update was not process, please check your entity", HttpStatus.BAD_REQUEST, track);
//        }
    }

    @Override
    public void delete(int id) {
        this.trackDao.deleteById(id);

//        if (!deleteWasSuccessful) {
//            throw new WrongRequestException("The delete was not process, please check the entity that you want to delete", HttpStatus.BAD_REQUEST, id);
//        }
    }

    @Override
    public List<Track> getAll() {
        return new ArrayList<>(this.trackDao.findAll());
    }

    @Override
    public List<Track> getAllByMediaType(MediaType mediaType) {
//        return this.trackDao.getByMediaType(mediaType);

        return null;
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

    private void executePrevalidations(Track track){
//        if(!this.artistDao.validateAllArtistsExists(track.getArtists())){
//            throw new InvalidBusinessLogicFieldException("Some artists does not exists in the database");
//        }

    }
}
