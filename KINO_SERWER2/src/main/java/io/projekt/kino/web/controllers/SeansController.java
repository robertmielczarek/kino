package io.projekt.kino.web.controllers;

import io.projekt.kino.dtos.SeansDTO;
import io.projekt.kino.dtos.SeansDTO;
import io.projekt.kino.entities.Seans;
import io.projekt.kino.repositories.SeansRepository;
import io.projekt.kino.services.KinoService;
import io.projekt.kino.services.SeansService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

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
@RequestMapping(value = "/seans/")
public class SeansController {

	@Autowired 
	private SeansService seansService;
	@Autowired
	private SeansRepository seansRepository;

	@InitBinder
	private void dataBinder(WebDataBinder binder) {
		//yyyy-MM-dd hh:mm:ss
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		binder.registerCustomEditor(Date.class, editor);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/list", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<SeansDTO> findAll() {
		return seansService.findAllSeansDTO();
	}
		

	@RequestMapping(method = RequestMethod.DELETE, value = "/{seansId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void deleteFilm(@PathVariable("seansId") int seansId) {
		seansService.deleteSeans(seansId);
	}
	
	@RequestMapping(value="/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Integer create(@RequestBody SeansDTO seansDTO) {
		 return seansService.create(seansDTO);
	}
	
	@RequestMapping(value="/{seansId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public boolean update(@RequestBody SeansDTO seansDTO, @PathVariable Integer seansId) {
		 seansDTO.setiDShow(seansId);
		 seansService.update(seansDTO);
		 return true;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{seansId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public SeansDTO get(@PathVariable Integer seansId) {
		return seansService.findSeans(seansId);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/title/{title}/{cinemaId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Seans> getShowsByTitle(
			@PathVariable("title") String title,@PathVariable("cinemaId") int cinemaId){
		return seansRepository.getShowsByTitle("%"+title.toLowerCase()+"%",cinemaId);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/genre/{genre}/{cinemaId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Seans> getShowsByGenre(
			@PathVariable("genre") String genre,@PathVariable("cinemaId") int cinemaId){
		return seansRepository.getShowsByGenre(genre,cinemaId);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/date/{date}/{cinemaId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Seans> getShowsByDate(
			@PathVariable("date") Date date,@PathVariable("cinemaId") int cinemaId){
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date);
		cal2.setTime(date);
		cal2.add(Calendar.DATE, 1);
		setZero(cal1);
		setZero(cal2);
		return seansRepository.getShowsByTime(cal1.getTime(),cal2.getTime(),cinemaId);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/time/{time}/{hoursDiff}/{cinemaId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Seans> getShowsByTime(
			@PathVariable("time") Date time,
			@PathVariable("hoursDiff") int hoursDiff,
			@PathVariable("cinemaId") int cinemaId){
		Calendar cal = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal.setTime(time);
		cal2.setTime(time);
		cal.add(Calendar.HOUR, -hoursDiff);
		cal2.add(Calendar.HOUR, hoursDiff);
		return seansRepository.getShowsByTime(cal.getTime(),cal2.getTime(),cinemaId);
	}
	
	private Calendar setZero(Calendar calendar){
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar;
	}
}
