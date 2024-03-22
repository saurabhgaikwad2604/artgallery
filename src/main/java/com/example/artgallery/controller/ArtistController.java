package com.example.artgallery.controller;

import com.example.artgallery.model.*;
import com.example.artgallery.service.ArtistJpaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
public class ArtistController {
	@Autowired
	public ArtistJpaService artistService;

	@GetMapping("/galleries/artists")
	public ArrayList<Artist> getArtists() {
		return artistService.getArtists();
	}

	@GetMapping("/galleries/artists/{artistId}")
	public Artist getArtist(@PathVariable("artistId") int id) {
		return artistService.getArtist(id);
	}

	@PostMapping("/galleries/artists")
	public Artist addArtist(@RequestBody Artist artist) {
		return artistService.addArtist(artist);
	}

	@PutMapping("/galleries/artists/{artistId}")
	public Artist updateArtist(@PathVariable("artistId") int id, @RequestBody Artist artist) {
		return artistService.updateArtist(id, artist);
	}

	@DeleteMapping("/galleries/artists/{artistId}")
	public void deleteArtist(@PathVariable("artistId") int id) {
		artistService.deleteArtist(id);

	}

	@GetMapping("/artists/{artistId}/arts")
	public List<Art> getArtistArt(@PathVariable("artistId") int id) {
		return artistService.getArtistArt(id);
	}

	@GetMapping("/artists/{artistId}/galleries")
	public List<Gallery> getArtistGallery(@PathVariable("artistId") int id) {
		return artistService.getArtistGallery(id);
	}

}