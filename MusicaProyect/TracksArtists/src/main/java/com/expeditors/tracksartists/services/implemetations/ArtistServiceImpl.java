package com.expeditors.tracksartists.services.implemetations;

import com.expeditors.tracksartists.dataAccessObjects.IArtistDao;
import com.expeditors.tracksartists.dataAccessObjects.ITrackDao;
import com.expeditors.tracksartists.exceptionHandlers.exceptions.WrongRequestException;
import com.expeditors.tracksartists.models.Artist;
import com.expeditors.tracksartists.models.Track;
import com.expeditors.tracksartists.services.interfaces.IArtistService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistServiceImpl implements IArtistService {

    public final IArtistDao artistDao;

    public ArtistServiceImpl(IArtistDao artistDao) {
        this.artistDao = artistDao;
    }

    @Override
    public Artist add(Artist artist) {
        return this.artistDao.save(artist);
    }

    @Override
    public Artist getById(int id){
        Optional<Artist> artist = this.artistDao.findById(id);

        if(artist.isEmpty()){
            throw new WrongRequestException("There's not an artist with that id.", HttpStatus.NOT_FOUND, id);
        }

        return artist.get();
    }

    @Override
    public void update(Artist artist) {
        this.artistDao.save(artist);
    }

    @Override
    public void delete(int id) {
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
    public List<Track> getTracksByArtist(int idArtist) {
        return this.artistDao.getReferenceById(idArtist).getTracks().stream().toList();
    }
}
