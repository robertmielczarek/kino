package io.projekt.kino.web.controllers;

import io.projekt.kino.dtos.MiejsceBooleanDTO;
import io.projekt.kino.dtos.MiejsceDTO;
import io.projekt.kino.dtos.SeansDTO;
import io.projekt.kino.repositories.SeansRepository;
import io.projekt.kino.services.AdditionalService;
import io.projekt.kino.services.KinoService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value="/additional/")
public class AdditionalController {
	
	@InitBinder
	private void dataBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		binder.registerCustomEditor(Date.class, editor);
	}
	
	@Autowired
	private AdditionalService additionalService;
	
	@RequestMapping(method = RequestMethod.GET, value="/miastaikina", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<String> getCityAndCinemas() {
		return additionalService.getCityAndCinemas();
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/showlist/{nazwa_miasta}/{nazwa_kina}/{date}/{ID_Movie}", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<SeansDTO> getShowList(@PathVariable String nazwa_miasta,@PathVariable String nazwa_kina,
			@PathVariable Date date,@PathVariable int ID_Movie){
		return additionalService.getShowList(nazwa_miasta, nazwa_kina, date, ID_Movie);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/showlist/{nazwa_miasta}/{nazwa_kina}/{date}", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<SeansDTO> getShowList(@PathVariable String nazwa_miasta,
			@PathVariable String nazwa_kina, @PathVariable Date date) {
		return additionalService.getShowList(nazwa_miasta, nazwa_kina, date);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/seatlist/{showId}", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<String> getSeatList(@PathVariable int showId) {
		return additionalService.getSeatList(showId);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/seatmap/{showId}", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<MiejsceBooleanDTO> getSeatMap(@PathVariable int showId) {
		return additionalService.getSeatMap(showId);
	}
}
