package io.projekt.kino.web.controllers;

import io.projekt.kino.dtos.SalaDTO;
import io.projekt.kino.dtos.SalaDTO;
import io.projekt.kino.dtos.SalaDTO;
import io.projekt.kino.services.KinoService;
import io.projekt.kino.services.SalaService;
import io.projekt.kino.services.SeansService;

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
@RequestMapping(value = "/salas/")
public class SalaController {

	@Autowired 
	private SalaService salaService;

	@InitBinder
	private void dataBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		binder.registerCustomEditor(Date.class, editor);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/list", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<SalaDTO> findAll() {
		return salaService.findAllSalasDTO();
	}
		

	@RequestMapping(method = RequestMethod.DELETE, value = "/{salaId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void deleteFilm(@PathVariable("salaId") int salaId) {
		salaService.deleteSala(salaId);
	}
	
	@RequestMapping(value="/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Integer create(@RequestBody SalaDTO salaDTO) {
		 return salaService.create(salaDTO);
	}
	
	@RequestMapping(value="/{salaId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public boolean update(@RequestBody SalaDTO salaDTO, @PathVariable Integer salaId) {
		 salaDTO.setiDhall(salaId);
		 salaService.update(salaDTO);
		 return true;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{salaId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public SalaDTO get(@PathVariable Integer salaId) {
		return salaService.findSala(salaId);
	}
}
