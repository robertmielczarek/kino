package io.projekt.kino.web.controllers;

import io.projekt.kino.config.DBInitializer;
import io.projekt.kino.dtos.BiletDTO;
import io.projekt.kino.entities.Bilet;
import io.projekt.kino.repositories.BiletRepository;
import io.projekt.kino.services.BiletService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/bilets/")
public class BiletController {

	private static Logger logger = LoggerFactory
			.getLogger(BiletController.class);

	@Autowired
	private BiletService biletService;

	@Autowired
	private BiletRepository biletRepository;

	@InitBinder
	private void dataBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		binder.registerCustomEditor(Date.class, editor);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<BiletDTO> findAll() {
		return biletService.findAllBiletsDTO();
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{biletId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void deleteFilm(@PathVariable("biletId") int biletId) {
		biletService.deleteBilet(biletId);
	}

	@RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Integer create(@RequestBody BiletDTO biletDTO) {
		return biletService.create(biletDTO);
	}

	@RequestMapping(value = "/{biletId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public boolean update(@RequestBody BiletDTO biletDTO,
			@PathVariable Integer biletId) {
		biletDTO.setiDTicket(biletId);
		biletService.update(biletDTO);
		return true;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{biletId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public BiletDTO get(@PathVariable Integer biletId) {
		return biletService.findBilet(biletId);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/movie/{startDate}/{endDate}/{movieId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Integer getSoldTicketsByMovie(
			@PathVariable("startDate") Date startDate,
			@PathVariable("endDate") Date endDate,
			@PathVariable("movieId") Integer movieId,
			@RequestParam("cinemaId") Integer cinemaId) {
//		setDates(startDate, endDate);
		if (cinemaId != null)
			return biletRepository.getSoldTicketsByMovie(startDate, endDate,
					movieId, cinemaId);
		else
			return biletRepository.getSoldTicketsByMovie(startDate, endDate,
					movieId);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/day/{startDate}/{endDate}/{dayOfWeek}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Integer getSoldTicketsByDay(
			@PathVariable("startDate") Date startDate,
			@PathVariable("endDate") Date endDate,
			@PathVariable("dayOfWeek") int dayOfWeek,
			@RequestParam("cinemaId") Integer cinemaId) {
//		setDates(startDate, endDate);
		if (cinemaId != null)
			return biletRepository.getSoldTicketsByDay(startDate, endDate,
					cinemaId, dayOfWeek);
		else
			return biletRepository.getSoldTicketsByDay(startDate, endDate,
					dayOfWeek);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/genre/{startDate}/{endDate}/{genre}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Integer getSoldTicketsByGenre(
			@PathVariable("startDate") Date startDate,
			@PathVariable("endDate") Date endDate,
			@PathVariable("genre") String genre,
			@RequestParam("cinemaId") Integer cinemaId) {
//		setDates(startDate, endDate);
		if (cinemaId != null)
			return biletRepository.getSoldTicketsByGenre(startDate, endDate,
					genre, cinemaId);
		else
			return biletRepository.getSoldTicketsByGenre(startDate, endDate,
					genre);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/cashier/{startDate}/{endDate}/{cashierId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Integer getSoldTicketsByCashier(
			@PathVariable("startDate") Date startDate,
			@PathVariable("endDate") Date endDate,
			@PathVariable("cashierId") int cashierId) {
//		setDates(startDate, endDate);
		return biletRepository.getSoldTicketsByCashier(startDate, endDate,
				cashierId);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/cinema/{startDate}/{endDate}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Integer getSoldTicketsByCinema(
			@PathVariable("startDate") Date startDate,
			@PathVariable("endDate") Date endDate,
			@RequestParam("cinemaId") Integer cinemaId) {
//		setDates(startDate, endDate);
		if (cinemaId != null) {
			return biletRepository.getSoldTicketsByCinema(startDate, endDate,
					cinemaId);
		} else
			return biletRepository.getSoldTicketsByCinema(startDate, endDate);

	}

	@RequestMapping(method = RequestMethod.GET, value = "/money/{startDate}/{endDate}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Double getMoneyEarnedByCinema(
			@PathVariable("startDate") Date startDate,
			@PathVariable("endDate") Date endDate,
			@RequestParam("cinemaId") Integer cinemaId) {
//		setDates(startDate, endDate);
		if (cinemaId != null) {
			return biletRepository.getMoneyEarnedByCinema(startDate, endDate,
					cinemaId);
		} else
			return biletRepository.getMoneyEarnedByCinema(startDate, endDate);
	}

/*	private void setDates(Date startDate, Date endDate) {
		if (startDate == null)
			startDate = new Date(0);
		if (endDate == null)
			endDate = new Date();
	}*/

}
