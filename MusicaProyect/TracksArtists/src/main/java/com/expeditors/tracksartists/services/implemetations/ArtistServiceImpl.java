package com.expeditors.tracksartists.services.implemetations;

import com.expeditors.tracksartists.dataAccessObjects.Interfaces.IArtistDao;
import com.expeditors.tracksartists.dataAccessObjects.Interfaces.ITrackDao;
import com.expeditors.tracksartists.exceptionHandlers.exceptions.WrongRequestException;
import com.expeditors.tracksartists.models.Artist;
import com.expeditors.tracksartists.models.Track;
import com.expeditors.tracksartists.services.interfaces.IArtistService;
import com.expeditors.tracksartists.services.interfaces.ITrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("profile1")
public class ArtistServiceImpl implements IArtistService {

    @Autowired public IArtistDao artistDao;
    @Autowired public ITrackDao trackDao;

    @Override
    public Artist add(Artist artist) {
        return this.artistDao.add(artist);
    }

    @Override
    public Artist getById(int id){
        Artist artist = this.artistDao.getById(id);

        if(artist == null){
            throw new WrongRequestException("Track not found with the specific id", HttpStatus.NOT_FOUND, id);
        }

        return artist;
    }

    @Override
    public void update(Artist artist) {
        boolean updateWasSuccessful = this.artistDao.update(artist);

        if(!updateWasSuccessful){
            throw new WrongRequestException("The update was not process, please check your entity", HttpStatus.BAD_REQUEST, artist);
        }
    }

    @Override
    public void delete(int id) {
        boolean deleteWasSuccessful = this.artistDao.delete(id);

        if (!deleteWasSuccessful) {
            throw new WrongRequestException("The delete was not process, please check the entity that you want to delete", HttpStatus.BAD_REQUEST, id);
        }
    }

    public List<Artist> getAll(){
        return this.artistDao.getAll();
    }

    public List<Artist> getArtistsByIds(List<Integer> artistsIds){
        return this.artistDao.getArtistsByIdList(artistsIds);
    }

    @Override
    public List<Artist> getArtistByName(String name) {
        return this.artistDao.getByName(name);
    }

    @Override
    public List<Track> getTracksByArtist(int idArtist) {
        return this.trackDao.getAll().stream().filter(track -> track.getArtists().contains(idArtist)).toList();
    }
}
