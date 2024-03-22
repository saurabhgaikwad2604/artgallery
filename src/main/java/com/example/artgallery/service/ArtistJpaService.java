package com.example.artgallery.service;

import com.example.artgallery.model.*;
import com.example.artgallery.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ArtistJpaService implements ArtistRepository {
	@Autowired
	private ArtistJpaRepository artistJpaRepository;
	@Autowired
	private GalleryJpaRepository galleryJpaRepository;
	@Autowired
	private ArtJpaRepository artJpaRepository;

	@Override
	public ArrayList<Artist> getArtists() {
		List<Artist> artistList = artistJpaRepository.findAll();
		ArrayList<Artist> artists = new ArrayList<>(artistList);
		return artists;
	}

	@Override
	public Artist getArtist(int id) {
		try {
			Artist artist = artistJpaRepository.findById(id).get();
			return artist;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public Artist addArtist(Artist artist) {
		try {
			List<Integer> galleryIds = new ArrayList<>();
			for (Gallery gallery : artist.getGalleries()) {
				galleryIds.add(gallery.getId());
			}
			List<Gallery> galleries = galleryJpaRepository.findAllById(galleryIds);
			if (galleryIds.size() != galleries.size()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
			}
			artist.setGalleries(galleries);

			for (Gallery gallery : galleries) {
				gallery.getArtists().add(artist);
			}
			Artist savedArtist = artistJpaRepository.save(artist);
			galleryJpaRepository.saveAll(galleries);
			return savedArtist;

		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public Artist updateArtist(int id, Artist artist) {
		try {
			Artist newArtist = artistJpaRepository.findById(id).get();
			if (artist.getName() != null) {
				newArtist.setName(artist.getName());
			}
			if (artist.getGenre() != null) {
				newArtist.setGenre(artist.getGenre());
			}
			if (artist.getGalleries() != null) {
				List<Gallery> galleries = newArtist.getGalleries();
				for (Gallery gallery : galleries) {
					gallery.getArtists().remove(newArtist);
				}
				galleryJpaRepository.saveAll(galleries);

				List<Integer> newGalleryIds = new ArrayList<>();
				for (Gallery gallery : artist.getGalleries()) {
					newGalleryIds.add(gallery.getId());
				}
				List<Gallery> newGalleries = galleryJpaRepository.findAllById(newGalleryIds);
				if (newGalleryIds.size() != newGalleries.size()) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
				}

				for (Gallery gallery : newGalleries) {
					gallery.getArtists().add(newArtist);
				}
				galleryJpaRepository.saveAll(newGalleries);
				newArtist.setGalleries(newGalleries);
			}
			return artistJpaRepository.save(newArtist);

		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public void deleteArtist(int id) {
		try {
			Artist artist = artistJpaRepository.findById(id).get();

			List<Gallery> galleries = artist.getGalleries();
			for (Gallery gallery : galleries) {
				gallery.getArtists().remove(artist);
			}
			galleryJpaRepository.saveAll(galleries);

			artistJpaRepository.deleteById(id);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		throw new ResponseStatusException(HttpStatus.NO_CONTENT);
	}

	@Override
	public List<Art> getArtistArt(int id) {
		Artist artist = artistJpaRepository.findById(id).get();
		List<Art> arts = artJpaRepository.findByArtist(artist);
		return arts;
	}

	@Override
	public List<Gallery> getArtistGallery(int id) {
		Artist artist = artistJpaRepository.findById(id).get();
		List<Gallery> galleries = artist.getGalleries();
		return galleries;
	}

}