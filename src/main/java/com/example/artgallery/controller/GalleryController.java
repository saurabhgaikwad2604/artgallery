package com.example.artgallery.controller;

import com.example.artgallery.model.*;
import com.example.artgallery.service.GalleryJpaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
public class GalleryController {
	@Autowired
	public GalleryJpaService galleryService;

	@GetMapping("/galleries")
	public ArrayList<Gallery> getGalleries() {
		return galleryService.getGalleries();
	}

	@GetMapping("/galleries/{galleryId}")
	public Gallery getGallery(@PathVariable("galleryId") int id) {
		return galleryService.getGallery(id);
	}

	@PostMapping("/galleries")
	public Gallery addGallery(@RequestBody Gallery gallery) {
		return galleryService.addGallery(gallery);
	}

	@PutMapping("/galleries/{galleryId}")
	public Gallery updateGallery(@PathVariable("galleryId") int id, @RequestBody Gallery gallery) {
		return galleryService.updateGallery(id, gallery);
	}

	@DeleteMapping("/galleries/{galleryId}")
	public void deleteGallery(@PathVariable("galleryId") int id) {
		galleryService.deleteGallery(id);

	}

	@GetMapping("/galleries/{galleryId}/artists")
	public List<Artist> getGalleryArtist(@PathVariable("galleryId") int id) {
		return galleryService.getGalleryArtist(id);
	}

}