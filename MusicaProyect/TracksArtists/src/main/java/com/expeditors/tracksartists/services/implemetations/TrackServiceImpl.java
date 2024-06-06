package com.expeditors.tracksartists.services.implemetations;

import com.expeditors.tracksartists.dataAccessObjects.IArtistDao;
import com.expeditors.tracksartists.dataAccessObjects.ITrackDao;
import com.expeditors.tracksartists.enums.DEvaluation;
import com.expeditors.tracksartists.enums.MediaType;
import com.expeditors.tracksartists.exceptionHandlers.exceptions.WrongRequestException;
import com.expeditors.tracksartists.models.Artist;
import com.expeditors.tracksartists.models.Track;
import com.expeditors.tracksartists.services.interfaces.ITrackService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Duration;
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
        List<Artist> artists = track.getArtists().stream().toList();
        track.getArtists().clear();

        artists.forEach(curretArtist -> {
            Optional<Artist> artist = this.artistDao.findById(curretArtist.getId());

            if(artist.isEmpty()){
                curretArtist = this.artistDao.save(curretArtist);
                track.getArtists().add(curretArtist);
            } else{
                track.getArtists().add(artist.get());
            }
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
        if(!this.trackDao.existsById(track.getId())){
            throw new WrongRequestException("Track not found with the specific id", HttpStatus.NOT_FOUND, track.getId());
        }
        this.trackDao.save(track);
    }

    @Override
    public void delete(int id) {
        this.trackDao.findById(id).ifPresent(track ->
            track.getArtists().forEach(artist -> artist.getTracks().remove(track))
        );

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
        return this.trackDao.findAllByIssueDate(year);
    }

    @Override
    public List<Artist> getArtistByTrack(int idTrack) {
        return this.trackDao.findArtistsByTrack(idTrack);
    }

    @Override
    public List<Track> getByDurationDynamic(Integer seconds, DEvaluation dEvaluation) {
        Duration duration = Duration.ofSeconds(seconds);

        return switch (dEvaluation){
            case DEvaluation.SHORTER -> this.trackDao.findByDurationLessThan(duration);
            case DEvaluation.EQUAL -> this.trackDao.findByDurationEquals(duration);
            case DEvaluation.LONGER -> this.trackDao.findByDurationGreaterThan(duration);
        };
    }
}
