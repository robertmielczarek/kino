package io.projekt.kino.web.controllers;

import io.projekt.kino.dtos.FilmDTO;
import io.projekt.kino.dtos.KinoDTO;
import io.projekt.kino.dtos.SeansDTO;
import io.projekt.kino.services.KinoService;

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
@RequestMapping(value="/kinos/")
public class KinoController
{
	@Autowired
	private KinoService kinoService;

	@InitBinder
	private void dataBinder(WebDataBinder binder)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		binder.registerCustomEditor(Date.class, editor);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/list", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<KinoDTO> findAll() {
		return kinoService.findAllCinemasDTO();
	}
		

	@RequestMapping(method = RequestMethod.DELETE, value = "/{cinemaId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void deleteFilm(@PathVariable("cinemaId") int cinemaId) {
		kinoService.deleteCinema(cinemaId);
	}
	
	@RequestMapping(value="/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Integer create(@RequestBody KinoDTO kinoDTO) {
		 return kinoService.create(kinoDTO);
	}
	
	@RequestMapping(value="/{cinemaId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public boolean update(@RequestBody KinoDTO kinoDTO, @PathVariable Integer cinemaId) {
		 kinoDTO.setiDCinema(cinemaId);
		 kinoService.update(kinoDTO);
		 return true;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{cinemaId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public KinoDTO getUser(@PathVariable Integer cinemaId) {
		return kinoService.findCinema(cinemaId);
	}
}
