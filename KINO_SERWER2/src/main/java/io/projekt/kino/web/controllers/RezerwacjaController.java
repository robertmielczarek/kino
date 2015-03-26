package io.projekt.kino.web.controllers;

import io.projekt.kino.dtos.BiletDTO;
import io.projekt.kino.dtos.RezerwacjaDTO;
import io.projekt.kino.services.BiletService;
import io.projekt.kino.services.RezerwacjaService;

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
@RequestMapping(value="/rezerwacjas/")
public class RezerwacjaController {

	@Autowired 
	private RezerwacjaService rezerwacjaService;

	@InitBinder
	private void dataBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		binder.registerCustomEditor(Date.class, editor);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/list", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<RezerwacjaDTO> findAll() {
		return rezerwacjaService.findAllRezerwacjasDTO();
	}
		

	@RequestMapping(method = RequestMethod.DELETE, value = "/{rezerwacjaId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void deleteFilm(@PathVariable("rezerwacjaId") int rezerwacjaId) {
		rezerwacjaService.deleteRezerwacja(rezerwacjaId);
	}
	
	@RequestMapping(value="/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Integer create(@RequestBody RezerwacjaDTO rezerwacjaDTO) {
		 return rezerwacjaService.create(rezerwacjaDTO);
	}
	
	@RequestMapping(value="/{rezerwacjaId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public boolean update(@RequestBody RezerwacjaDTO rezerwacjaDTO, @PathVariable Integer rezerwacjaId) {
		 rezerwacjaDTO.setiDReservation(rezerwacjaId);
		 rezerwacjaService.update(rezerwacjaDTO);
		 return true;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{rezerwacjaId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public RezerwacjaDTO get(@PathVariable Integer rezerwacjaId) {
		return rezerwacjaService.findRezerwacja(rezerwacjaId);
	}
}
	
