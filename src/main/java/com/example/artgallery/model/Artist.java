package com.example.artgallery.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.*;

@Data
@Entity
@Table(name = "artist")
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String genre;
    @ManyToMany
    @JoinTable(name = "artist_gallery", joinColumns = @JoinColumn(name = "artistid"), inverseJoinColumns = @JoinColumn(name = "galleryid"))
    @JsonIgnoreProperties("artists")
    private List<Gallery> galleries = new ArrayList<>();
}