package io.projekt.kino.web.controllers;

import io.projekt.kino.dtos.MiejsceDTO;
import io.projekt.kino.dtos.SalaDTO;
import io.projekt.kino.services.MiejsceService;
import io.projekt.kino.services.SalaService;

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
@RequestMapping(value = "/miejscas/")
public class MiejsceController {

	@Autowired 
	private MiejsceService miejsceService;

	@InitBinder
	private void dataBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		binder.registerCustomEditor(Date.class, editor);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/list", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<MiejsceDTO> findAll() {
		return miejsceService.findAllMiejscasDTO();
	}
		

	@RequestMapping(method = RequestMethod.DELETE, value = "/{miejsceId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void deleteFilm(@PathVariable("miejsceId") int miejsceId) {
		miejsceService.deleteMiejsce(miejsceId);
	}
	
	@RequestMapping(value="/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Integer create(@RequestBody MiejsceDTO miejsceDTO) {
		 return miejsceService.create(miejsceDTO);
	}
	
	@RequestMapping(value="/{miejsceId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public boolean update(@RequestBody MiejsceDTO miejsceDTO, @PathVariable Integer miejsceId) {
		 miejsceDTO.setiDSeat(miejsceId);
		 miejsceService.update(miejsceDTO);
		 return true;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{miejsceId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public MiejsceDTO get(@PathVariable Integer miejsceId) {
		return miejsceService.findMiejsce(miejsceId);
	}
}
