package com.example.artgallery.repository;

import com.example.artgallery.model.*;
import java.util.*;

public interface GalleryRepository {
    ArrayList<Gallery> getGalleries();

    Gallery getGallery(int id);

    Gallery addGallery(Gallery gallery);

    Gallery updateGallery(int id, Gallery gallery);

    void deleteGallery(int id);

    List<Artist> getGalleryArtist(int id);
}