package io.projekt.kino.web.controllers;

import io.projekt.kino.dtos.BiletDTO;
import io.projekt.kino.dtos.ZnizkaDTO;
import io.projekt.kino.services.BiletService;
import io.projekt.kino.services.ZnizkaService;

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
@RequestMapping(value="/znizkas/")
public class ZnizkaController {


	@Autowired 
	private ZnizkaService znizkaService;

	@InitBinder
	private void dataBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		binder.registerCustomEditor(Date.class, editor);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/list", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<ZnizkaDTO> findAll() {
		return znizkaService.findAllZnizkasDTO();
	}
		

	@RequestMapping(method = RequestMethod.DELETE, value = "/{znizkaId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void delete(@PathVariable("znizkaId") int znizkaId) {
		znizkaService.deleteZnizka(znizkaId);
	}
	
	@RequestMapping(value="/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Integer create(@RequestBody ZnizkaDTO znizkaDTO) {
		 return znizkaService.create(znizkaDTO);
	}
	
	@RequestMapping(value="/{znizkaId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public boolean update(@RequestBody ZnizkaDTO znizkaDTO, @PathVariable Integer znizkaId) {
		 znizkaDTO.setiDDiscount(znizkaId);
		 znizkaService.update(znizkaDTO);
		 return true;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{znizkaId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ZnizkaDTO get(@PathVariable Integer znizkaId) {
		return znizkaService.findZnizka(znizkaId);
	}
}
