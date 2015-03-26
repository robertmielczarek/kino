package io.projekt.kino.web.controllers;

import io.projekt.kino.dtos.FilmDTO;
import io.projekt.kino.services.FilmService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/films/")
public class FilmController
{
	@Autowired
	private FilmService filmService;

	@InitBinder
	private void dataBinder(WebDataBinder binder)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		binder.registerCustomEditor(Date.class, editor);
	}
	@RequestMapping(method = RequestMethod.GET, value="/list", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<FilmDTO> findAll() {
		return filmService.findAllFilmsDTO();
	}
		

	@RequestMapping(method = RequestMethod.DELETE, value = "/{filmId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void deleteFilm(@PathVariable("filmId") int filmId) {
		filmService.deleteFilm(filmId);
	}
	
	@RequestMapping(value="/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Integer create(@RequestBody FilmDTO filmDTO) {
		 return filmService.create(filmDTO);
	}
	
	@RequestMapping(value="/{filmId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public boolean update(@RequestBody FilmDTO filmDTO, @PathVariable Integer filmId) {
		 filmDTO.setiDMovie(filmId);
		 filmService.update(filmDTO);
		 return true;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{filmId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public FilmDTO getUser(@PathVariable Integer filmId) {
		return filmService.findFilm(filmId);
	}
}
