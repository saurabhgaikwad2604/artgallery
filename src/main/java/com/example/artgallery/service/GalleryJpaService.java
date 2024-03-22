package com.example.artgallery.service;

import com.example.artgallery.model.*;
import com.example.artgallery.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class GalleryJpaService implements GalleryRepository {
	@Autowired
	private GalleryJpaRepository galleryJpaRepository;

	@Autowired
	private ArtistJpaRepository artistJpaRepository;

	@Override
	public ArrayList<Gallery> getGalleries() {
		List<Gallery> galleryList = galleryJpaRepository.findAll();
		ArrayList<Gallery> galleries = new ArrayList<>(galleryList);
		return galleries;
	}

	@Override
	public Gallery getGallery(int id) {
		try {
			Gallery gallery = galleryJpaRepository.findById(id).get();
			return gallery;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public Gallery addGallery(Gallery gallery) {
		try {
			List<Integer> artistIds = new ArrayList<>();
			for (Artist artist : gallery.getArtists()) {
				artistIds.add(artist.getId());
			}
			List<Artist> artists = artistJpaRepository.findAllById(artistIds);
			if (artistIds.size() != artists.size()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
			}
			gallery.setArtists(artists);

			for (Artist artist : artists) {
				artist.getGalleries().add(gallery);
			}
			Gallery savedGallery = galleryJpaRepository.save(gallery);
			artistJpaRepository.saveAll(artists);
			return savedGallery;
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public Gallery updateGallery(int id, Gallery gallery) {
		try {
			Gallery newGallery = galleryJpaRepository.findById(id).get();
			if (gallery.getName() != null) {
				newGallery.setName(gallery.getName());
			}
			if (gallery.getLocation() != null) {
				newGallery.setLocation(gallery.getLocation());
			}
			if (gallery.getArtists() != null) {
				List<Artist> artists = newGallery.getArtists();
				for (Artist artist : artists) {
					artist.getGalleries().remove(newGallery);
				}
				artistJpaRepository.saveAll(artists);

				List<Integer> newArtistIds = new ArrayList<>();
				for (Artist artist : gallery.getArtists()) {
					newArtistIds.add(artist.getId());
				}
				List<Artist> newArtists = artistJpaRepository.findAllById(newArtistIds);
				if (newArtistIds.size() != newArtists.size()) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
				}

				for (Artist artist : newArtists) {
					artist.getGalleries().add(newGallery);
				}
				artistJpaRepository.saveAll(newArtists);
				newGallery.setArtists(newArtists);
			}
			return newGallery;
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public void deleteGallery(int id) {
		try {
			Gallery gallery = galleryJpaRepository.findById(id).get();

			List<Artist> artists = gallery.getArtists();
			for (Artist artist : artists) {
				artist.getGalleries().remove(gallery);
			}
			artistJpaRepository.saveAll(artists);

			galleryJpaRepository.deleteById(id);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		throw new ResponseStatusException(HttpStatus.NO_CONTENT);
	}

	@Override
	public List<Artist> getGalleryArtist(int id) {
		Gallery gallery = galleryJpaRepository.findById(id).get();
		List<Artist> artists = gallery.getArtists();
		return artists;
	}

}