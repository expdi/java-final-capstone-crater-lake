package com.expeditors.tracksartists.services.implemetations;

import com.expeditors.tracksartists.dataAccessObjects.IArtistDao;
import com.expeditors.tracksartists.dataAccessObjects.ITrackDao;
import com.expeditors.tracksartists.exceptionHandlers.exceptions.WrongRequestException;
import com.expeditors.tracksartists.models.Artist;
import com.expeditors.tracksartists.models.Track;
import com.expeditors.tracksartists.services.interfaces.IArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistServiceImpl implements IArtistService {

    public final IArtistDao artistDao;
    public final ITrackDao trackDao;

    public ArtistServiceImpl(IArtistDao artistDao, ITrackDao trackDao) {
        this.artistDao = artistDao;
        this.trackDao = trackDao;
    }

    @Override
    public Artist add(Artist artist) {
        return this.artistDao.save(artist);
    }

    @Override
    public Artist getById(int id){
        Artist artist = this.artistDao.getReferenceById(id);

        if(artist == null){
            throw new WrongRequestException("Track not found with the specific id", HttpStatus.NOT_FOUND, id);
        }

        return artist;
    }

    @Override
    public void update(Artist artist) {
        this.artistDao.save(artist);

//        if(!updateWasSuccessful){
//            throw new WrongRequestException("The update was not process, please check your entity", HttpStatus.BAD_REQUEST, artist);
//        }
    }

    @Override
    public void delete(int id) {
         this.artistDao.deleteById(id);
//
//        if (!deleteWasSuccessful) {
//            throw new WrongRequestException("The delete was not process, please check the entity that you want to delete", HttpStatus.BAD_REQUEST, id);
//        }
    }

    public List<Artist> getAll(){
        return this.artistDao.findAll();
    }

    public List<Artist> getArtistsByIds(List<Integer> artistsIds){
//        return this.artistDao.getArtistsByIdList(artistsIds);
        return null;
    }

    @Override
    public List<Artist> getArtistByName(String name) {
        return this.artistDao.getByName(name);
    }

    @Override
    public List<Track> getTracksByArtist(int idArtist) {
        return this.trackDao.findAll().stream().filter(track -> track.getArtists().contains(idArtist)).toList();
    }
}
