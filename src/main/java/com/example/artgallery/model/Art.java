package com.example.artgallery.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "art")
public class Art {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String theme;
    @ManyToOne
    @JoinColumn(name = "artistid")
    private Artist artist;
}