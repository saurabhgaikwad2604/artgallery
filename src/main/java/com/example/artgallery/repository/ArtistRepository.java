package com.example.artgallery.repository;

import com.example.artgallery.model.*;
import java.util.*;

public interface ArtistRepository {
    ArrayList<Artist> getArtists();

    Artist getArtist(int id);

    Artist addArtist(Artist artist);

    Artist updateArtist(int id, Artist artist);

    void deleteArtist(int id);

    List<Art> getArtistArt(int id);

    List<Gallery> getArtistGallery(int id);
}