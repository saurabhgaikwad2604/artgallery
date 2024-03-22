package com.example.artgallery.repository;

import com.example.artgallery.model.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface ArtJpaRepository extends JpaRepository<Art, Integer> {
    ArrayList<Art> findByArtist(Artist artist);
}