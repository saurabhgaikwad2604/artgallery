package com.example.artgallery.controller;

import com.example.artgallery.model.*;
import com.example.artgallery.service.ArtJpaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
public class ArtController {
	@Autowired
	public ArtJpaService artService;

	@GetMapping("/galleries/artists/arts")
	public ArrayList<Art> getArts() {
		return artService.getArts();
	}

	@GetMapping("/galleries/artists/arts/{artId}")
	public Art getArt(@PathVariable("artId") int id) {
		return artService.getArt(id);
	}

	@PostMapping("/galleries/artists/arts")
	public Art addArt(@RequestBody Art art) {
		return artService.addArt(art);
	}

	@PutMapping("/galleries/artists/arts/{artId}")
	public Art updateArt(@PathVariable("artId") int id, @RequestBody Art art) {
		return artService.updateArt(id, art);
	}

	@DeleteMapping("/galleries/artists/arts/{artId}")
	public void deleteArt(@PathVariable("artId") int id) {
		artService.deleteArt(id);
	}

	@GetMapping("/arts/{artId}/artist")
	public Artist getArtArtist(@PathVariable("artId") int id) {
		return artService.getArtArtist(id);
	}

}