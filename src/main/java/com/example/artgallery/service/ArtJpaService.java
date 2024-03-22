package com.example.artgallery.service;

import com.example.artgallery.model.*;
import com.example.artgallery.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ArtJpaService implements ArtRepository {
    @Autowired
    private ArtJpaRepository artJpaRepository;

    @Autowired
    private ArtistJpaRepository artistJpaRepository;

    @Override
    public ArrayList<Art> getArts() {
        List<Art> artList = artJpaRepository.findAll();
        ArrayList<Art> arts = new ArrayList<>(artList);
        return arts;
    }

    @Override
    public Art getArt(int id) {
        try {
            Art art = artJpaRepository.findById(id).get();
            return art;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Art addArt(Art art) {
        Artist artist = art.getArtist();
        int artistId = artist.getId();
        try {
            Artist newArtist = artistJpaRepository.findById(artistId).get();
            art.setArtist(newArtist);
            artJpaRepository.save(art);
            return art;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Art updateArt(int id, Art art) {
        try {
            Art newArt = artJpaRepository.findById(id).get();
            if (art.getTitle() != null) {
                newArt.setTitle(art.getTitle());
            }
            if (art.getTheme() != null) {
                newArt.setTheme(art.getTheme());
            }
            if (art.getArtist() != null) {
                Artist artist = art.getArtist();
                int artistId = artist.getId();
                Artist newArtist = artistJpaRepository.findById(artistId).get();
                newArt.setArtist(newArtist);
            }
            return artJpaRepository.save(newArt);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteArt(int id) {
        try {
            artJpaRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public Artist getArtArtist(int id) {
        Art art = artJpaRepository.findById(id).get();
        Artist artists = art.getArtist();
        return artists;
    }
}
