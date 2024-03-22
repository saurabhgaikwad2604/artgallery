package com.example.artgallery.repository;

import com.example.artgallery.model.*;
import java.util.*;

public interface ArtRepository {
    ArrayList<Art> getArts();

    Art getArt(int id);

    Art addArt(Art art);

    Art updateArt(int id, Art art);

    void deleteArt(int id);

    Artist getArtArtist(int id);
}