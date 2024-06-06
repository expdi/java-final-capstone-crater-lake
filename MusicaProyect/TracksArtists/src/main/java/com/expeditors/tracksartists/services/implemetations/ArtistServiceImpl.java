package com.expeditors.tracksartists.services.implemetations;

import com.expeditors.tracksartists.dataAccessObjects.IArtistDao;
import com.expeditors.tracksartists.dataAccessObjects.ITrackDao;
import com.expeditors.tracksartists.models.Artist;
import com.expeditors.tracksartists.models.Track;
import com.expeditors.tracksartists.services.interfaces.IArtistService;
import com.expeditors.tracksartists.utils.EntityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistServiceImpl implements IArtistService {

    @Autowired private IArtistDao artistDao;

    @Autowired private ITrackDao trackDao;

    @Override
    public Artist add(Artist artist) {
        return this.artistDao.save(artist);
    }

    @Override
    public Artist getById(int id){
        return EntityValidator.getIfIsValidEntity(this.artistDao, id);
    }

    @Override
    public void update(Artist artist) {
        EntityValidator.getIfIsValidEntity(this.artistDao, artist.getId());
        this.artistDao.save(artist);
    }

    @Override
    public void delete(int id) {
        Artist artist = EntityValidator.getIfIsValidEntity(this.artistDao, id);
        this.trackDao.getAllTracksByArtistId(id).forEach(track -> track.getArtists().remove(artist));
        this.artistDao.deleteById(id);
    }

    public List<Artist> getAll(){
        return this.artistDao.findAll();
    }

    public List<Artist> getArtistsByIds(List<Integer> artistsIds){
        return this.artistDao.getByIdIn(artistsIds);
    }

    @Override
    public List<Artist> getArtistByName(String name) {
        return this.artistDao.getByNameContaining(name);
    }

    @Override
    public List<Track> getTracksByArtist(int id) {
        Artist artist = EntityValidator.getIfIsValidEntity(this.artistDao, id);
        return artist.getTracks().stream().toList();
    }
}
