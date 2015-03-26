package io.projekt.kino.services;

import io.projekt.kino.dtos.FilmDTO;
import io.projekt.kino.entities.Film;
import io.projekt.kino.entities.Seans;
import io.projekt.kino.repositories.FilmRepository;
import io.projekt.kino.repositories.SeansRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FilmService {

	@Autowired
	private FilmRepository filmRepository;
	@Autowired
	private SeansRepository seansRepository;

	public List<Film> findAllFilms() {
		return filmRepository.findAll();
	}
	
	public List<FilmDTO> findAllFilmsDTO(){
		List<FilmDTO> filmsDTO = new ArrayList<FilmDTO>();
		
		for(Film film: filmRepository.findAll()){
			FilmDTO filmDTO = new FilmDTO();
			filmDTO.setiDMovie(film.getIDMovie());
			filmDTO.setGenre(film.getGenre());
			filmDTO.setLenght(film.getLenght());
			filmDTO.setDescription(film.getDescription());
			filmDTO.setTitle(film.getTitle());
			for(Seans seans: film.getSeansList()){
				filmDTO.getSeansIds().add(seans.getIDShow());
			}
			filmsDTO.add(filmDTO);
		}
		return filmsDTO;
	}

	public void deleteFilm(int filmId) {
		filmRepository.delete(filmId);
	}

	public Integer create(FilmDTO filmDTO) {
		Film film = new Film();
		film.setDescription(filmDTO.getDescription());
		film.setGenre(filmDTO.getGenre());
		film.setLenght(filmDTO.getLenght());
		film.setTitle(filmDTO.getTitle());
		film.setSeansList(seansRepository.findAll(filmDTO.getSeansIds()));
		return filmRepository.save(film).getIDMovie();
	}

	public void update(FilmDTO filmDTO) {
		Film film = filmRepository.findOne(filmDTO.getiDMovie());
		film.setDescription(filmDTO.getDescription());
		film.setGenre(filmDTO.getGenre());
		film.setLenght(filmDTO.getLenght());
		film.setTitle(filmDTO.getTitle());
		film.setSeansList(seansRepository.findAll(filmDTO.getSeansIds()));
		filmRepository.save(film);
	}

	public FilmDTO findFilm(Integer filmId) {
		FilmDTO filmDTO = new FilmDTO();
		Film film = filmRepository.findOne(filmId);
		filmDTO.setiDMovie(film.getIDMovie());
		filmDTO.setGenre(film.getGenre());
		filmDTO.setLenght(film.getLenght());
		filmDTO.setDescription(film.getDescription());
		filmDTO.setTitle(film.getTitle());
		for(Seans seans: film.getSeansList()){
			filmDTO.getSeansIds().add(seans.getIDShow());
		}
		return filmDTO;
	}

}
