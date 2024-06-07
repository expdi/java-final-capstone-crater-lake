package com.expeditors.tracksartists.services.implemetations;

import com.expeditors.tracksartists.dataAccessObjects.IArtistDao;
import com.expeditors.tracksartists.dataAccessObjects.ITrackDao;
import com.expeditors.tracksartists.enums.DEvaluation;
import com.expeditors.tracksartists.enums.MediaType;
import com.expeditors.tracksartists.models.Artist;
import com.expeditors.tracksartists.models.Track;
import com.expeditors.tracksartists.services.interfaces.ITrackService;
import com.expeditors.tracksartists.utils.EntityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
public class TrackServiceImpl implements ITrackService {

    @Autowired private ITrackDao trackDao;
    @Autowired private IArtistDao artistDao;

    @Override
    public Track add(Track track){
        List<Artist> artists = track.getArtists().stream().toList();
        track.getArtists().clear();

        for (Artist curretArtist: artists) {
            this.artistDao.findById(curretArtist.getId()).ifPresentOrElse(artist -> track.getArtists().add(artist), () -> track.getArtists().add(this.artistDao.save(curretArtist)));
        }

        return trackDao.save(track);
    }

    @Override
    public Track getById(int id){
        return  EntityValidator.getIfIsValidEntity(this.trackDao, id);
    }

    @Override
    public void update(Track track) {
        EntityValidator.getIfIsValidEntity(this.trackDao, track.getId());
        this.trackDao.save(track);
    }

    @Override
    public void delete(int id) {
        EntityValidator.getIfIsValidEntity(this.trackDao, id);

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
        EntityValidator.getIfIsValidEntity(this.trackDao, idTrack);
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
